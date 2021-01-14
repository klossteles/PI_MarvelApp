package com.marvelapp06.marvelapp.login.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.marvelapp06.marvelapp.R

class RegisterFragment : Fragment() {
    private lateinit var _view: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view

        _view.findViewById<ImageView>(R.id.imgBackRegister).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }

    companion object {
    }
}