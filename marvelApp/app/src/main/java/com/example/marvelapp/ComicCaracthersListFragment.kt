package com.example.marvelapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.marvelapp.data.model.CharactersList

class ComicCaracthersListFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic_caracthers_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(characters: CharactersList?) =
                ComicCaracthersListFragment().apply {
                    arguments = Bundle().apply {
                        putString("COMIC_CHARACTERS", characters.toString())
                    }
                }
    }
}