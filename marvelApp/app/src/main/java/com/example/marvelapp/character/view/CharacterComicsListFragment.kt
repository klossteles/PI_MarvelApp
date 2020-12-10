package com.example.marvelapp.character.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvelapp.R
import com.example.marvelapp.data.model.ComicsList


class CharacterComicsListFragment : Fragment() {
    private lateinit var _view: View
    private var _comics: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _comics=it.getString("CHARACTER_COMICS")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_comics_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view=view

    }
    companion object {

        fun newInstance(comics: ComicsList?) =
            CharacterComicsListFragment().apply {
                arguments = Bundle().apply {
                    putString("CHARACTER_COMICS",comics.toString())
                }
            }
    }
}