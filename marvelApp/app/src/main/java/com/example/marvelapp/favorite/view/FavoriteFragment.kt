package com.example.marvelapp.favorite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.marvelapp.R
import com.example.marvelapp.character.model.CharactersModel
import com.example.marvelapp.character.view.CharacterComicsListFragment
import com.example.marvelapp.character.view.CharacterDescriptionFragment
import com.example.marvelapp.character.view.CharacterSeriesListFragment
import com.example.marvelapp.character.view.CharacterViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class FavoriteFragment : Fragment() {

    private lateinit var _view : View
    private lateinit var _viewModel:CharactersModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = _view.findViewById<ViewPager>(R.id.favoritesViewPager)
        val tab = _view.findViewById<TabLayout>(R.id.favoritesTabLayout)

        tab.setupWithViewPager(viewPager)

        val fragmentFavorites = mutableListOf<Fragment>()

        val titles = listOf("Personagens", "Séries", "HQs", "Criadores")


        fragmentFavorites.add(FavoriteCharacterFragment())
        fragmentFavorites.add(FavoriteSeriesFragment())
        fragmentFavorites.add(FavoriteComicFragment())
        fragmentFavorites.add(FavoriteCreatorFragment())

        viewPager.adapter = activity?.supportFragmentManager?.let { it ->
            CharacterViewPagerAdapter(fragmentFavorites, titles, it)
        }

    }
}
