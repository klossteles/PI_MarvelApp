package com.example.marvelapp.series.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.marvelapp.R
import com.example.marvelapp.series.repository.SeriesRepository
import com.example.marvelapp.series.viewmodel.SeriesViewModel
import com.google.android.material.tabs.TabLayout
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        _viewModel = ViewModelProvider(
            this,
            SeriesViewModel.SeriesViewModelFactory(SeriesRepository())
        ).get(SeriesViewModel::class.java)

        val pager = _view.findViewById<ViewPager>(R.id.seriesViewPager)
        val tab = _view.findViewById<TabLayout>(R.id.seriesTabLayout)

        tab.setupWithViewPager(pager)
        val fragments = mutableListOf<Fragment>()

        val titles = listOf(getString(R.string.description), getString(R.string.creators), getString(
                    R.string.caracters), getString(R.string.comics))

        showLoading(true)
        val txtSeriesDetailsTitle = _view.findViewById<TextView>(R.id.txtSeriesDetailsTitle)
        val image = _view.findViewById<ImageView>(R.id.imgSeriesDetails)
        val seriesId = arguments?.getInt(SeriesListFragment.SERIES_ID)

        if (seriesId != null) {
            _viewModel.getSeriesById(seriesId).observe(viewLifecycleOwner, Observer {
                showLoading(false)
                txtSeriesDetailsTitle.text = it.title
                if (it.thumbnail == null || it.thumbnail.path.contains("image_not_available")) {
                    Picasso.get()
                        .load(R.drawable.image_not_available)
                        .into(image)
                } else {
                    val imagePath = "${it.thumbnail.path}/landscape_large.${it.thumbnail.extension}"
                    Picasso.get()
                        .load(imagePath)
                        .into(image)
                }
                fragments.add(SeriesDescriptionFragment.newInstance(it.description))
                fragments.add(SeriesCreatorListFragment.newInstance(it.creators))
                fragments.add(SeriesCaracthersListFragment.newInstance(it.characters))
                fragments.add(SeriesComicsListFragment.newInstance(it.comics))
                pager.adapter = activity?.supportFragmentManager?.let { it1 ->
                    ViewPageAdapter(fragments, titles,
                        it1
                    )
                }
            })
        }
        showLoading(false)
        setBackNavigation()
    }

    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgSeriesDetailsBack).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = _view.findViewById<View>(R.id.seriesDetailsLoading)

        if (isLoading) {
            viewLoading.visibility = View.VISIBLE
        } else {
            viewLoading.visibility = View.GONE
        }
    }
}