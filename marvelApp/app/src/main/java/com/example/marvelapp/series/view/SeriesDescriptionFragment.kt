package com.example.marvelapp.series.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.marvelapp.R

class SeriesDescriptionFragment : Fragment() {
    private lateinit var _view: View

    private var _description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _description = it.getString("SERIES_DESCRIPTION")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        _view.findViewById<TextView>(R.id.txtSeriesDescription).text = _description
    }

    companion object {
        @JvmStatic
        fun newInstance(description: String?) =
            SeriesDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString("SERIES_DESCRIPTION", description)
                }
            }

    }
}