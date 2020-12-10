package com.example.marvelapp.creator.view

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.marvelapp.R
import com.example.marvelapp.creator.model.CreatorsModel
import com.example.marvelapp.creator.repository.CreatorsRepository
import com.example.marvelapp.creator.viewmodel.CreatorsViewModel
import com.example.marvelapp.data.model.CharacterSummary
import com.example.marvelapp.data.model.CreatorSummary
import com.example.marvelapp.series.view.*
import com.example.marvelworld.api.models.ComicSummary
import com.example.marvelworld.api.models.EventSummary
import com.example.marvelworld.api.models.SeriesSummary
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

class CreatorsFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _viewModel: CreatorsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_creators, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view
        _viewModel = ViewModelProvider(
            this,
            CreatorsViewModel.CreatorsViewModelFactory(CreatorsRepository())
        ).get(CreatorsViewModel::class.java)

        val textCreatorsNameDetails = _view.findViewById<TextView>(R.id.txtFullnameCreatorsDetails)
        val image = _view.findViewById<ImageView>(R.id.imgCreatorDetails)
        val cgSeriesCreators = _view.findViewById<ChipGroup>(R.id.cgSeriesCreators)
        val cgComicsCreators = _view.findViewById<ChipGroup>(R.id.cgComicsCreators)
        val cgEventsCreators = _view.findViewById<ChipGroup>(R.id.cgEventsCreators)

        val thumbnail = arguments?.getString(CreatorsListFragment.CREATORS_THUMBNAIL)
        val fullname = arguments?.getString(CreatorsListFragment.CREATORS_FULLNAME)
        val series = arguments?.get(CreatorsListFragment.CREATORS_SERIES)
        val comics = arguments?.get(CreatorsListFragment.CREATORS_COMICS)
        val events = arguments?.get(CreatorsListFragment.CREATORS_EVENTS)

        textCreatorsNameDetails.text = fullname

        Picasso.get().load(thumbnail).into(image)

        if ((series as List<SeriesSummary>).size >0){
            for (serie in series as List<SeriesSummary>){
                val chip = Chip(_view.context)
                if (serie.name != null) {
                    chip.text = "${serie.name}"
                } else {
                    chip.text = serie.name
                }
                cgSeriesCreators.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtSeriesCreatorsDetails).visibility = View.GONE
        }


        if ((comics as List<ComicSummary>).size >0){
            for (comic in comics as List<ComicSummary>){
                val chip = Chip(_view.context)
                if (comic.name != null) {
                    chip.text = "${comic.name}"
                } else {
                    chip.text = comic.name
                }
                cgComicsCreators.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtComicsCreatorsDetails).visibility = View.GONE
        }

        if ((events as List<EventSummary>).size >0){
            for (event in events as List<EventSummary>){
                val chip = Chip(_view.context)
                if (event.name != null) {
                    chip.text = "${event.name}"
                } else {
                    chip.text = event.name
                }
                cgEventsCreators.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtEventsCreatorsDetails).visibility = View.GONE
        }

        setBackNavigation()
        setOnFavoriteClick()
    }

    private fun setOnFavoriteClick() {
        val seriesFavorites = _view.findViewById<ImageView>(R.id.imgCreatorsDetailsFavorite)
        seriesFavorites.setOnClickListener {
            seriesFavorites.setColorFilter(
                ContextCompat.getColor(_view.context, R.color.color_red),
                PorterDuff.Mode.SRC_IN
            );
        }
    }

    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgCreatorsDetailsBack).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }
}