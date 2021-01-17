package com.marvelapp06.marvelapp.character.view

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.character.repository.CharacterRepository
import com.marvelapp06.marvelapp.character.viewmodel.CharacterViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.marvelapp06.marvelapp.character.model.CharactersModel
import com.marvelapp06.marvelapp.data.model.*
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel
import com.squareup.picasso.Picasso
import java.util.*

class CharacterFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _viewModel: CharacterViewModel
    private lateinit var _viewModelFavorite: FavoriteViewModel

    private var _idCharacter: Int? = null
    private lateinit var _characterModelJson: String
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view
        _viewModel = ViewModelProvider(
            this,
            CharacterViewModel.CharacterViewModelFactory(CharacterRepository())
        ).get(CharacterViewModel::class.java)

        val txtCharacterDetailsName = _view.findViewById<TextView>(R.id.txtCharacterName)
        val image = _view.findViewById<ImageView>(R.id.imageCharacter)
        val txtDescription = _view.findViewById<TextView>(R.id.txtDescriptionCharacters)
        val cgComics = _view.findViewById<ChipGroup>(R.id.cgComicsCharacters)
        val cgSeries = _view.findViewById<ChipGroup>(R.id.cgSeriesCharacters)

        _idCharacter = arguments?.getInt(CharacterListFragment.CHARACTER_ID)
        val name = arguments?.getString(CharacterListFragment.CHARACTER_NAME)
        val thumbnail = arguments?.getString(CharacterListFragment.CHARACTER_THUMBNAIL)
        val description = arguments?.getString(CharacterListFragment.CHARACTER_DESCRIPTION)
        val comics = arguments?.get(CharacterListFragment.CHARACTER_COMIC)
        val series = arguments?.get(CharacterListFragment.CHARACTER_SERIES)

        _characterModelJson = arguments?.getString(CharacterListFragment.CHARACTER_MODEL_JSON)!!

        txtCharacterDetailsName.text = name
        txtDescription.text = description
        Picasso.get().load(thumbnail).into(image)

        if ((comics as List<ComicSummary>).size > 0) {
            for (comic in comics as List<ComicSummary>) {
                val chip = Chip(_view.context)
                chip.text = comic.name
                cgComics.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtComicsCharacters).visibility = View.GONE
        }

        if ((series as List<SeriesSummary>).size > 0) {
            for (serie in series as List<SeriesSummary>) {
                val chip = Chip(_view.context)
                chip.text = serie.name
                cgSeries.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtSeriesCharacters).visibility = View.GONE
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


        setBackNavigation()
        setOnFavoriteClick()

    }

    private fun setOnFavoriteClick() {

        val charactersFavorites = _view.findViewById<ImageView>(R.id.imgCharactersDetailsFavorite)
        charactersFavorites.setOnClickListener {

            isFavorite = !isFavorite

            var color: Int
            if(isFavorite) {
                color = R.color.color_red
                if (_idCharacter != null) {
                    _viewModelFavorite.addFavorite(
                        _idCharacter!!,
                        _characterModelJson,
                        1
                    ).observe(viewLifecycleOwner, Observer {
                        Snackbar.make(_view, "Personagem favoritado", Snackbar.LENGTH_LONG).show()
                    })
                }
            } else {
                color =  R.color.color_white
                Snackbar.make(_view, "Personagem removido dos favoritos", Snackbar.LENGTH_LONG)
                    .show()
            }

            charactersFavorites.setColorFilter(
                ContextCompat.getColor(_view.context, color),
                PorterDuff.Mode.SRC_IN
            );
        }
    }

    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgCharactersDetailsBack).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }
}
