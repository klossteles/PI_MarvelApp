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
import com.example.marvelapp.R
import com.example.marvelapp.series.repository.SeriesRepository
import com.example.marvelapp.series.viewmodel.SeriesViewModel
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