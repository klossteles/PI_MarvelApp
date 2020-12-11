package com.group06.marvelapp.character.view

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
import androidx.navigation.fragment.findNavController
import com.group06.marvelapp.R
import com.group06.marvelapp.character.repository.CharacterRepository
import com.group06.marvelapp.character.viewmodel.CharacterViewModel
import com.group06.marvelapp.data.model.ComicSummary
import com.group06.marvelapp.data.model.SeriesSummary
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso

class CharacterFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _viewModel: CharacterViewModel


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

        val name = arguments?.getString(CharacterListFragment.CHARACTER_NAME)
        val thumbnail = arguments?.getString(CharacterListFragment.CHARACTER_THUMBNAIL)
        val description = arguments?.getString(CharacterListFragment.CHARACTER_DESCRIPTION)
        val comics = arguments?.get(CharacterListFragment.CHARACTER_COMIC)
        val series = arguments?.get(CharacterListFragment.CHARACTER_SERIES)

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

        setBackNavigation()
        setOnFavoriteClick()
    }

    private fun setOnFavoriteClick() {
        val seriesFavorites = _view.findViewById<ImageView>(R.id.imgCharactersDetailsFavorite)
        seriesFavorites.setOnClickListener {
            seriesFavorites.setColorFilter(
                ContextCompat.getColor(_view.context, R.color.color_red),
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
