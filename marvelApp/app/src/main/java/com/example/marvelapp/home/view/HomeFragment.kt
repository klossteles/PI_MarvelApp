package com.example.marvelapp.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.marvelapp.R
import com.example.marvelapp.character.model.CharactersModel
import com.example.marvelapp.character.view.CharacterListAdapter
import com.example.marvelapp.character.view.CharacterListFragment.Companion.CHARACTER_COMIC
import com.example.marvelapp.character.view.CharacterListFragment.Companion.CHARACTER_DESCRIPTION
import com.example.marvelapp.character.view.CharacterListFragment.Companion.CHARACTER_ID
import com.example.marvelapp.character.view.CharacterListFragment.Companion.CHARACTER_MODIFIED
import com.example.marvelapp.character.view.CharacterListFragment.Companion.CHARACTER_NAME
import com.example.marvelapp.character.view.CharacterListFragment.Companion.CHARACTER_RESOURCEURI
import com.example.marvelapp.character.view.CharacterListFragment.Companion.CHARACTER_SERIES
import com.example.marvelapp.character.view.CharacterListFragment.Companion.CHARACTER_THUMBNAILS
import com.example.marvelapp.character.view.CharacterListFragment.Companion.CHARACTER_URLS
import com.example.marvelapp.series.view.SeriesListFragment.Companion.SERIES_ID
import com.google.android.material.card.MaterialCardView


class HomeFragment : Fragment() {
    private lateinit var _view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        _view.findViewById<MaterialCardView>(R.id.crdCharacterHome).setOnClickListener {
            _view.findNavController().navigate(R.id.action_homeFragment_to_characterListFragment)
        }
                _view.findViewById<MaterialCardView>(R.id.crdSeriesHome).setOnClickListener {
                    _view.findNavController()
                        .navigate(R.id.action_homeFragment_to_seriesListFragment)
                }
                   _view.findViewById<MaterialCardView>(R.id.crdComicHome).setOnClickListener {
                         _view.findNavController()
                              .navigate(R.id.action_homeFragment_to_searchComicFragment)
                   }
            }
        }

