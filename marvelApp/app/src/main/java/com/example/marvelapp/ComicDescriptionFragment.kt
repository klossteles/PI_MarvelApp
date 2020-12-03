package com.example.marvelapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ComicDescriptionFragment : Fragment() {
    private lateinit var _view: View

    private var _description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _description = it.getString("COMIC_DESCRIPTION")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comic_description, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        _view.findViewById<TextView>(R.id.txtComicDescription).text = _description

    }
//    companion object {
//        @JvmStatic
//        fun newInstance(description: String?) =
//                SeriesDescriptionFragment().apply {
//                    arguments = Bundle().apply {
//                        putString("COMIC_DESCRIPTION", description)
//                    }
//                }
//    }
}