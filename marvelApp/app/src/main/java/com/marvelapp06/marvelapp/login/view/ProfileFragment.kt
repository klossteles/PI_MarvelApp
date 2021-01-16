package com.marvelapp06.marvelapp.login.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.marvelapp06.marvelapp.LoginActivity
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.R

class ProfileFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        _auth = FirebaseAuth.getInstance()
        onSignOut()
    }

    private fun onSignOut() {
        _view.findViewById<ImageView>(R.id.imgLogout).setOnClickListener {
            _auth.signOut()
            val intent = Intent(_view.context, LoginActivity::class.java)
            startActivityForResult(intent, LoginFragment.REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginFragment.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val loggedIn = data!!.getBooleanExtra(LoginFragment.LOGGED_IN, false)
                if (loggedIn) {
                    val user = _auth.currentUser
                    // Update user information
                } else {
                    Toast.makeText(_view.context,
                        getString(R.string.its_necessary_to_logged_to_access_profile),
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(_view.context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }
    }
}