package com.group06.marvelapp.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.group06.marvelapp.MainActivity
import com.group06.marvelapp.R
import com.group06.marvelapp.favorite.view.FavoritesActivity

class LoginFragment : Fragment() {
    private lateinit var _view:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view

        val hide = arguments?.getBoolean(HIDE_BACK)
        if (hide != null && hide) {
            hideBackIcon()
        }

        _view.findViewById<Button>(R.id.containedButtonLogin).setOnClickListener {
            val pref = _view.context.getSharedPreferences(MainActivity.MARVEL_APP, Context.MODE_PRIVATE)
            pref.edit().putBoolean(MainActivity.KEEP_LOGGED, true).apply()
            val intent = Intent(_view.context, FavoritesActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun hideBackIcon() {
        _view.findViewById<ImageView>(R.id.imgBackLogin).visibility = View.GONE
    }

    companion object {
        const val HIDE_BACK = "HIDE_BACK"
    }
}