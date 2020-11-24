package com.example.marvelapp.series.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvelapp.R
import com.example.marvelapp.data.model.ComicsList

class SeriesComicsListFragment : Fragment() {
    private lateinit var _view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series_comics_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
    }

    companion object {
        @JvmStatic
        fun newInstance(comics: ComicsList?) =
            SeriesComicsListFragment().apply {
                arguments = Bundle().apply {
                    putString("SERIES_COMICS", comics.toString())
                }
            }
    }
}