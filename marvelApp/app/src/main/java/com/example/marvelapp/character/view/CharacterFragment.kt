package com.example.marvelapp.character.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.marvelapp.R
import com.example.marvelapp.character.repository.CharacterRepository
import com.example.marvelapp.character.viewmodel.CharacterViewModel
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

class CharacterFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _viewModel: CharacterViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view
        _viewModel = ViewModelProvider(
            this,
            CharacterViewModel.CharacterViewModelFactory(CharacterRepository())
        ).get(CharacterViewModel::class.java)

        val viewPager = _view.findViewById<ViewPager>(R.id.characterViewPager)
        val tab = _view.findViewById<TabLayout>(R.id.characterTabLayout)

        tab.setupWithViewPager(viewPager)
        val fragments = mutableListOf<Fragment>()

        val titles = listOf("Descrição", "HQs", "Séries")

        showLoading(true)

        val textCharacterName = _view.findViewById<TextView>(R.id.textCharacterName)
        val imageCharacter = _view.findViewById<ImageView>(R.id.imageCharacter)
        val characterId = arguments?.getInt(CharacterListFragment.CHARACTER_ID)

        if (characterId != null) {
            _viewModel.getCharactersById(characterId).observe(viewLifecycleOwner, Observer {
                showLoading(false)
                textCharacterName.text = it.name
                if (it.thumbnail == null || it.thumbnail.path.contains("image_not_available")) {
                    Picasso.get()
                        .load(R.drawable.image_not_available)
                        .into(imageCharacter)
                } else {
                    val imagePath = "${it.thumbnail.path}/landscape_large.${it.thumbnail.extension}"
                    Picasso.get()
                        .load(imagePath)
                        .into(imageCharacter)
                }
                fragments.add(CharacterDescriptionFragment.newInstance(it.description))
                fragments.add(CharacterComicsListFragment.newInstance(it.comics))
                fragments.add(CharacterSeriesListFragment.newInstance(it.series))
                viewPager.adapter = activity?.supportFragmentManager?.let { it ->
                    CharacterViewPagerAdapter(fragments, titles, it)
                }
            })
        }

        showLoading(false)
        setBackNavigation()

    }

    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgCharactersDetailsBack).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = _view.findViewById<View>(R.id.charactersDetailsLoading)

        if (isLoading) {
            viewLoading.visibility = View.VISIBLE
        } else {
            viewLoading.visibility = View.GONE
        }
    }
}