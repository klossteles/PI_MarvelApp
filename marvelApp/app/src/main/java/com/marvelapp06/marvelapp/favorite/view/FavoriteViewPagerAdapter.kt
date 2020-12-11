package com.marvelapp06.marvelapp.favorite.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FavoriteViewPagerAdapter(
    private val _fragments: List<Fragment>,
    private val _titleTabs: List<String>,
    manager: FragmentManager
) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = _fragments[position]

    override fun getCount() = _fragments.size

    override fun getPageTitle(position: Int) = _titleTabs[position]

}