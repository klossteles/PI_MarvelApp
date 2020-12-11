package com.group06.marvelapp.series.view

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.group06.marvelapp.R
import com.group06.marvelapp.data.model.CharacterSummary
import com.group06.marvelapp.series.repository.SeriesRepository
import com.group06.marvelapp.series.viewmodel.SeriesViewModel
import com.group06.marvelapp.data.model.ComicSummary
import com.group06.marvelapp.data.model.CreatorSummary
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso

class SeriesFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _viewModel: SeriesViewModel

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

        val thumbnail = arguments?.getString(SeriesListFragment.SERIES_THUMBNAIL)
        val description = arguments?.getString(SeriesListFragment.SERIES_DESCRIPTION)
        val title = arguments?.getString(SeriesListFragment.SERIES_TITLE)
        val characters = arguments?.get(SeriesListFragment.SERIES_CHARACTERS)
        val creators = arguments?.get(SeriesListFragment.SERIES_CREATORS)
        val comics = arguments?.get(SeriesListFragment.SERIES_COMICS)
        val startYear = arguments?.getInt(SeriesListFragment.SERIES_START)
        val endYear = arguments?.getInt(SeriesListFragment.SERIES_END)

        txtDescription.text = description
        txtSeriesDetailsTitle.text = title
        txtStartYear.text = startYear.toString()
        txtEndYear.text = endYear.toString()

        Picasso.get().load(thumbnail).into(image)

        if ((characters as List<CharacterSummary>).size > 0) {
            for (character in characters as List<CharacterSummary>){
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


        if ((characters as List<CreatorSummary>).size > 0) {
            for (creator in creators as List<CreatorSummary>){
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

        if ((characters as List<ComicSummary>).size > 0) {
            for (comic in comics as List<ComicSummary>){
                val chip = Chip(_view.context)
                chip.text = comic.name
                cgComics.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtComicsSeries).visibility = View.GONE
        }
        setBackNavigation()
        setOnFavoriteClick()
    }

    private fun setOnFavoriteClick() {
        val seriesFavorites = _view.findViewById<ImageView>(R.id.imgSeriesDetailsFavorite)
        seriesFavorites.setOnClickListener {
            seriesFavorites.setColorFilter(
                ContextCompat.getColor(_view.context, R.color.color_red),
                PorterDuff.Mode.SRC_IN
            );
        }
    }

    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgSeriesDetailsBack).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }
}