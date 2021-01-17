package com.marvelapp06.marvelapp.favorite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.marvelapp06.marvelapp.R
import com.google.android.material.tabs.TabLayout
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
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
            FavoriteViewPagerAdapter(fragmentFavorites, titles, it)
        }
    }
}
