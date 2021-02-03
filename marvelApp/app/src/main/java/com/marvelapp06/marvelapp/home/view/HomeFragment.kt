package com.marvelapp06.marvelapp.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.marvelapp06.marvelapp.R
import com.google.android.material.card.MaterialCardView
import com.marvelapp06.marvelapp.data.api.NetworkUtils
import com.marvelapp06.marvelapp.utils.NetworkConnection
import kotlinx.android.synthetic.main.activity_profile.*

class HomeFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _navController: NavController
    private lateinit var _networkConnection: NetworkConnection
    private var _hasConnection:Boolean = false

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
        _networkConnection = NetworkConnection(_view.context)
        _networkConnection.observe(viewLifecycleOwner, Observer { isConnected ->
            _hasConnection = isConnected
        })

        bindOnClick()
    }

    private fun bindOnClick() {
        _view.findViewById<MaterialCardView>(R.id.crdCharacterHome).setOnClickListener {
            navigateOrShowInternetError(R.id.action_homeFragment_to_characterListFragment)
        }
        _view.findViewById<MaterialCardView>(R.id.crdSeriesHome).setOnClickListener {
            navigateOrShowInternetError(R.id.action_homeFragment_to_seriesListFragment)
        }
        _view.findViewById<MaterialCardView>(R.id.crdComicHome).setOnClickListener {
            navigateOrShowInternetError(R.id.action_homeFragment_to_searchComicFragment)
        }

        _view.findViewById<MaterialCardView>(R.id.crdCreatorHome).setOnClickListener {
            navigateOrShowInternetError(R.id.action_homeFragment_to_creatorsListFragment)
        }

        _view.findViewById<MaterialCardView>(R.id.crdQuiz).setOnClickListener {
            navigateToAction(R.id.action_homeFragment_to_quizStartFragment2)
        }
    }

    private fun navigateOrShowInternetError(action: Int) {
        if (_hasConnection) {
            navigateToAction(action)
        } else {
            Toast.makeText(_view.context, "This action requires internet connection", Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToAction(action: Int) {
        _navController.navigate(action)
    }
}

