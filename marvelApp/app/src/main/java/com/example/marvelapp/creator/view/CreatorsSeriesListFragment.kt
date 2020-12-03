package com.example.marvelapp.creator.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvelapp.R
import com.example.marvelworld.api.models.SeriesList

class CreatorsSeriesListFragment : Fragment() {

    private lateinit var _view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_creators_series_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
    }

    companion object {
        @JvmStatic
        fun newInstance(series: SeriesList?) =
            CreatorsSeriesListFragment().apply {
                arguments = Bundle().apply {
                    putString("CREATORS_SERIES", series.toString())
                }
            }

    }
}