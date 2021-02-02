package com.marvelapp06.marvelapp.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.marvelapp06.marvelapp.R
import com.google.android.material.card.MaterialCardView
import com.marvelapp06.marvelapp.data.api.NetworkUtils

class HomeFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _navController: NavController

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
        _navController = findNavController()

        bindOnClick()
    }

    private fun bindOnClick() {
        _view.findViewById<MaterialCardView>(R.id.crdCharacterHome).setOnClickListener {
            navigateToAction(R.id.action_homeFragment_to_characterListFragment)
        }
        _view.findViewById<MaterialCardView>(R.id.crdSeriesHome).setOnClickListener {
            navigateToAction(R.id.action_homeFragment_to_seriesListFragment)
        }
        _view.findViewById<MaterialCardView>(R.id.crdComicHome).setOnClickListener {
            navigateToAction(R.id.action_homeFragment_to_searchComicFragment)
        }

        _view.findViewById<MaterialCardView>(R.id.crdCreatorHome).setOnClickListener {
                navigateToAction(R.id.action_homeFragment_to_creatorsListFragment)
        }

        _view.findViewById<MaterialCardView>(R.id.crdQuiz).setOnClickListener {
            navigateToAction(R.id.action_homeFragment_to_quizStartFragment2)
        }
    }

    private fun navigateToAction(action: Int) {
        _navController.navigate(action)
    }
}

