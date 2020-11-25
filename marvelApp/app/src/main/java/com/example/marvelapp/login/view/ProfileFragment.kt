package com.example.marvelapp.login.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.marvelapp.R

class ProfileFragment : Fragment() {

    private lateinit var _view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgBackProfile).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }

    companion object {

    }
}