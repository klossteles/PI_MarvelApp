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
        val serie = arguments?.getString(CreatorsListFragment.CREATORS_SERIES)
        val comic = arguments?.getString(CreatorsListFragment.CREATORS_COMICS)
        val event = arguments?.getString(CreatorsListFragment.CREATORS_EVENTS)

        textCreatorsNameDetails.text = fullname

        Picasso.get().load(thumbnail).into(image)

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