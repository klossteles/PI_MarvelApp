package com.example.marvelapp.character.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvelapp.R
import com.example.marvelworld.api.models.SeriesList

class CharacterSeriesListFragment : Fragment() {
    private lateinit var _view:View
    private var _series: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _series = it.getString("CHARACTER_SERIES")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_series_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view=view
    }

    companion object {
        fun newInstance(series: SeriesList?) =
            CharacterSeriesListFragment().apply {
                arguments = Bundle().apply {
                    putString("CHARACTER_SERIES", series.toString())

                }
            }
    }
}