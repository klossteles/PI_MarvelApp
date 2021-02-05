package com.marvelapp06.marvelapp.series.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.data.model.CharacterSummary
import com.marvelapp06.marvelapp.series.repository.SeriesRepository
import com.marvelapp06.marvelapp.series.viewmodel.SeriesViewModel
import com.marvelapp06.marvelapp.data.model.ComicSummary
import com.marvelapp06.marvelapp.data.model.CreatorSummary
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marvelapp06.marvelapp.login.view.LoginActivity
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel
import com.marvelapp06.marvelapp.fullscreen.view.FullscreenImageFragment.Companion.THUMBNAIL
import com.marvelapp06.marvelapp.login.view.LoginFragment
import com.squareup.picasso.Picasso

class SeriesFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _viewModel: SeriesViewModel
    private lateinit var _viewModelFavorite: FavoriteViewModel
    private lateinit var _auth: FirebaseAuth
    private var _idSeries: Int? = null
    private lateinit var _seriesModelJson: String
    private var isFavorite: Boolean = false
    private var color: Int? = null
    private lateinit var seriesFavorites: ImageView
    private  var _user:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        seriesFavorites = _view.findViewById(R.id.imgSeriesDetailsFavorite)
        _viewModel = ViewModelProvider(
            this,
            SeriesViewModel.SeriesViewModelFactory(SeriesRepository())
        ).get(SeriesViewModel::class.java)

        val txtSeriesDetailsTitle = _view.findViewById<TextView>(R.id.txtSeriesDetailsTitle)
        val image = _view.findViewById<ImageView>(R.id.imgSeriesDetails)
        val txtDescription = _view.findViewById<TextView>(R.id.txtDescriptionSeries)
        val txtStartYear = _view.findViewById<TextView>(R.id.txtStartSeries)
        val txtEndYear = _view.findViewById<TextView>(R.id.txtEndSeries)
        val cgCharacters = _view.findViewById<ChipGroup>(R.id.cgCharactersSeries)
        val cgComics = _view.findViewById<ChipGroup>(R.id.cgComicsSeries)
        val cgCreators = _view.findViewById<ChipGroup>(R.id.cgCreatorsSeries)

        _idSeries=arguments?.getInt(SeriesListFragment.SERIES_ID)
        val thumbnail = arguments?.getString(SeriesListFragment.SERIES_THUMBNAIL)
        val thumbnailPortrait = arguments?.getString(SeriesListFragment.SERIES_THUMBNAIL_FULLSCREEN, "")
        val description = arguments?.getString(SeriesListFragment.SERIES_DESCRIPTION)
        val title = arguments?.getString(SeriesListFragment.SERIES_TITLE)
        var characters:Any
        var creators:Any
        var comics:Any
        val startYear = arguments?.getInt(SeriesListFragment.SERIES_START)
        val endYear = arguments?.getInt(SeriesListFragment.SERIES_END)

        if(arguments?.get(SeriesListFragment.SERIES_CHARACTERS)== null) {
            characters = jsonToObjCharacters(arguments?.getString("SERIES_CHARACTERS_JSON")!!)
        } else {
            characters = arguments?.get(SeriesListFragment.SERIES_CHARACTERS)!!
        }

        if(arguments?.get(SeriesListFragment.SERIES_CREATORS)== null) {
            creators = jsonToObjCreators(arguments?.getString("SERIES_CREATORS_JSON")!!)
        } else {
            creators = arguments?.get(SeriesListFragment.SERIES_CREATORS)!!
        }

        if(arguments?.get(SeriesListFragment.SERIES_COMICS) == null) {
            comics = jsonToObjComics(arguments?.getString("SERIES_COMICS_JSON")!!)
        } else {
            comics = arguments?.get(SeriesListFragment.SERIES_COMICS)!!
        }

        _seriesModelJson = arguments?.getString(SeriesListFragment.SERIES_MODEL_JSON)!!

        txtDescription.text = description
        txtSeriesDetailsTitle.text = title
        txtStartYear.text = startYear.toString()
        txtEndYear.text = endYear.toString()

        Picasso.get().load(thumbnail).into(image)

        if ((characters as List<CharacterSummary>).size > 0) {
            for (character in characters){
                val chip = Chip(_view.context)
                if (character.role != null) {
                    chip.text = "${character.name} - ${character.role.capitalize()}"
                } else {
                    chip.text = character.name
                }
                cgCharacters.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtCreatorsSeries).visibility = View.GONE
        }


        if ((creators as List<CreatorSummary>).size > 0) {
            for (creator in creators ){
                val chip = Chip(_view.context)
                if (creator.role != null) {
                    chip.text = "${creator.name} - ${creator.role.capitalize()}"
                } else {
                    chip.text = creator.name
                }
                cgCreators.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtCharactersSeries).visibility = View.GONE
        }

        if ((comics as List<ComicSummary>).size > 0) {
            for (comic in comics ){
                val chip = Chip(_view.context)
                chip.text = comic.name
                cgComics.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtComicsSeries).visibility = View.GONE
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

        val currentUser = _auth.currentUser
        currentUser?.uid?.let {
            _viewModelFavorite.checkIfIsFavorite(it,_idSeries!!)
                .observe(viewLifecycleOwner, Observer { list ->

                    if (list.isEmpty()) {
                        color = R.color.color_white
                    } else {
                        isFavorite = true
                        color = R.color.color_red
                    }
                    seriesFavorites.setColorFilter(
                        ContextCompat.getColor(_view.context, color!!),
                        PorterDuff.Mode.SRC_IN
                    )
                })
        }

        if (thumbnailPortrait != null && thumbnailPortrait.isNotBlank()) {
            onImageClick(image, thumbnailPortrait)
        }
        setBackNavigation()
        setOnFavoriteClick()
    }

    private fun onImageClick(image: ImageView, thumbnailPortrait: String?) {
        image.setOnClickListener {
            val navController = findNavController()
            val bundle = bundleOf(THUMBNAIL to thumbnailPortrait)
            navController.navigate(R.id.action_seriesFragment_to_fullscreenImageFragment, bundle)
        }
    }

    private fun jsonToObjCharacters(json: String): Any {
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<List<CharacterSummary>>() {}.type

        return gson.fromJson(json, arrayTutorialType)
    }

    private fun jsonToObjCreators(json: String): Any {
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<List<CreatorSummary>>() {}.type

        return gson.fromJson(json, arrayTutorialType)
    }

    private fun jsonToObjComics(json: String): Any {
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<List<ComicSummary>>() {}.type

        return gson.fromJson(json, arrayTutorialType)
    }

    private fun setOnFavoriteClick() {
        val seriesFavorites = _view.findViewById<ImageView>(R.id.imgSeriesDetailsFavorite)
        seriesFavorites.setOnClickListener {
            val currentUser = _auth.currentUser

            if (currentUser != null) {
                _user=currentUser.uid
                favorite(_user!!)

            } else {
                val intent = Intent(context, LoginActivity::class.java)
                startActivityForResult(intent, LoginFragment.REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(LoginFragment.REQUEST_CODE==requestCode && Activity.RESULT_OK==resultCode){

            if(_user  != null){
                favorite(_user!!)
            }

        }
    }

    private fun favorite(user:String){

        isFavorite = !isFavorite

        if (isFavorite) {
            color = R.color.color_red
            if (_idSeries != null) {
                _viewModelFavorite.addFavorite(
                    user,
                    _idSeries!!,
                    _seriesModelJson,
                    2
                ).observe(viewLifecycleOwner, Observer {
                    Snackbar.make(_view, "Serie favoritado", Snackbar.LENGTH_LONG)
                        .show()
                })
            }
        } else {
            _idSeries?.let { it1 ->
                _viewModelFavorite.deleteFavorite( _user!!,it1)

                    .observe(viewLifecycleOwner, Observer {
                        Snackbar.make(
                            _view,
                            "Serie removida dos favoritos",
                            Snackbar.LENGTH_LONG
                        ).show()
                    })
            }
            color = R.color.color_white
        }

        seriesFavorites.setColorFilter(
            ContextCompat.getColor(_view.context, color!!),
            PorterDuff.Mode.SRC_IN
        )
    }

    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgSeriesDetailsBack).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }
}