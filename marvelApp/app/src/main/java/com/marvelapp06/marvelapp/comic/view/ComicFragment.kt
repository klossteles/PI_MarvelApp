package com.marvelapp06.marvelapp.comic.view

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.comic.model.ComicDate
import com.marvelapp06.marvelapp.comic.model.ComicPrice
import com.marvelapp06.marvelapp.comic.repository.ComicRepository
import com.marvelapp06.marvelapp.comic.viewModel.ComicViewModel
import com.marvelapp06.marvelapp.data.model.CharacterSummary
import com.marvelapp06.marvelapp.data.model.CreatorSummary
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel
import com.squareup.picasso.Picasso
import java.util.*

class ComicFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _viewModel: ComicViewModel

    private lateinit var _viewModelFavorite: FavoriteViewModel
    private lateinit var _auth: FirebaseAuth
    private var _idComic: Int? = null
    private lateinit var _comicModelJson: String
    private var isFavorite: Boolean = false
    private var color: Int? = null
    private lateinit var comicFavorites: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic, container, false)
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        comicFavorites=_view.findViewById(R.id.imgComicDetailsFavorite)
        _viewModel = ViewModelProvider(
                this,
                ComicViewModel.ComicViewModelFactory(ComicRepository())
        ).get(ComicViewModel::class.java)


        val txtComicDetailsTitle = _view.findViewById<TextView>(R.id.txtComicDetailsTitle)
        val imageComic = _view.findViewById<ImageView>(R.id.imgComicDetails)
        val txtDescriptionComic = _view.findViewById<TextView>(R.id.txtDescriptionComic)
        val cgCharactersComic = _view.findViewById<ChipGroup>(R.id.cgCharactersComic)
        val cgCreatorsComic = _view.findViewById<ChipGroup>(R.id.cgCreatorsComic)
        val txtPublishedDate = _view.findViewById<TextView>(R.id.txtPublishedDate)
        val txtPrice1 = _view.findViewById<TextView>(R.id.txtPrice1)
        val txtPage1 = _view.findViewById<TextView>(R.id.txtPage1)


        _idComic = arguments?.getInt(SearchComicFragment.COMIC_ID)
        val thumbnailComic = arguments?.getString(SearchComicFragment.COMIC_THUMBNAIL)
        val descriptionComic = arguments?.getString(SearchComicFragment.COMIC_DESCRIPTION)
        val titleComic = arguments?.getString(SearchComicFragment.COMIC_TITLE)
        val charactersComic = arguments?.get(SearchComicFragment.COMIC_CHARACTERS)
        val creatorsComic = arguments?.get(SearchComicFragment.COMIC_CREATORS)
        val datesComic = arguments?.get(SearchComicFragment.COMIC_DATES)
        val pricesComic = arguments?.get(SearchComicFragment.COMIC_PRICE)
        val pagesComic = arguments?.getInt(SearchComicFragment.COMIC_PAGES)

        _comicModelJson =arguments?.getString(SearchComicFragment.COMIC_MODEL_JSON)!!

        txtDescriptionComic.text = descriptionComic
        txtComicDetailsTitle.text = titleComic
        txtPage1.text = pagesComic?.toString()
        Picasso.get().load(thumbnailComic).into(imageComic)


        if (pricesComic != null) {
            txtPrice1.text = "$ ${(pricesComic as List<ComicPrice>)[0].price.toString()}"
        }
        if (datesComic != null) {
            for (date in datesComic as List<ComicDate>) {
                if (date.type?.contains("onsaleDate") == true){
                    val calendar = Calendar.getInstance()
                    calendar.time = date.date!!
                    txtPublishedDate.text = "${calendar.getDisplayName(
                            Calendar.MONTH,
                            Calendar.LONG,
                            Locale.getDefault()
                    )} ${calendar.get(Calendar.DAY_OF_MONTH)}, ${calendar.get(Calendar.YEAR)}"
                }
            }
        }

        if ((charactersComic as List<CharacterSummary>).size > 0) {
            for (character in charactersComic as List<CharacterSummary>){
                val chip = Chip(_view.context)
                if (character.role != null) {
                    chip.text = "${character.name} - ${character.role.capitalize()}"
                } else {
                    chip.text = character.name
                }
                cgCharactersComic.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtCreatorsComic).visibility = View.GONE
        }

        if ((charactersComic as List<CreatorSummary>).size > 0) {
            for (creator in creatorsComic as List<CreatorSummary>){
                val chip = Chip(_view.context)
                if (creator.role != null) {
                    chip.text = "${creator.name} - ${creator.role.capitalize()}"
                } else {
                    chip.text = creator.name
                }
                cgCreatorsComic.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtCharactersComic).visibility = View.GONE
        }


        _viewModelFavorite = ViewModelProvider(
            this,
            FavoriteViewModel.FavoriteViewModelFactory(
                FavoriteRepository(
                    AppDatabase.getDatabase(
                        _view.context
                    ).favoriteDao()
                )
            )
        ).get(FavoriteViewModel::class.java)

        _viewModelFavorite.checkIfIsFavorite(_idComic!!)
            .observe(viewLifecycleOwner, Observer { list ->

                if (list.isEmpty()) {
                    color = R.color.color_white
                } else {
                    isFavorite = true
                    color = R.color.color_red
                }
                comicFavorites.setColorFilter(
                    ContextCompat.getColor(_view.context, color!!),
                    PorterDuff.Mode.SRC_IN
                )
            })



        setBackNavigation()
        setOnFavoriteClick()
    }
    private fun setOnFavoriteClick() {
        comicFavorites.setOnClickListener {
            isFavorite = !isFavorite

            if (isFavorite) {
                color = R.color.color_red
                if (_idComic != null) {
                    _viewModelFavorite.addFavorite(
                        _idComic!!,
                        _comicModelJson,
                        3
                    ).observe(viewLifecycleOwner, Observer {
                        Snackbar.make(_view, "HQ favoritada", Snackbar.LENGTH_LONG)
                            .show()
                    })
                }
            } else {
                _idComic?.let { it1 ->
                    _viewModelFavorite.deleteFavorite(it1)
                        .observe(viewLifecycleOwner, Observer {
                            Snackbar.make(
                                _view,
                                "HQ removida dos favoritos",
                                Snackbar.LENGTH_LONG
                            ).show()
                        })
                }
                color = R.color.color_white
            }

            comicFavorites.setColorFilter(
                ContextCompat.getColor(_view.context, color!!),
                PorterDuff.Mode.SRC_IN
            )
        }
    }
    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgComicDetailsBack).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }

    }

}