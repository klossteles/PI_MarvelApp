package com.marvelapp06.marvelapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

class NoInternetFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_internet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        _navController = findNavController()
        setOnBack()
    }

    private fun setOnBack () {
        _view.findViewById<ImageView>(R.id.imgBackNoInternet).setOnClickListener {
            _navController.navigateUp()
        }
    }
}