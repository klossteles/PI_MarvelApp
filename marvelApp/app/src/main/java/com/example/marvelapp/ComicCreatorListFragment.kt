package com.example.marvelapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.marvelapp.data.model.CreatorsList


class ComicCreatorListFragment : Fragment() {
    private lateinit var _view: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic_creator_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(creators: CreatorsList?) =
                ComicCreatorListFragment().apply {
                    arguments = Bundle().apply {
                        putString("COMIC_CREATORS", creators.toString())
                    }
                }
    }
}