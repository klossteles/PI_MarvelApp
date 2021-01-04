package com.marvelapp06.marvelapp.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.view.FavoritesActivity

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var _view:View
    private lateinit var _callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _callbackManager = CallbackManager.Factory.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _view = inflater.inflate(R.layout.fragment_login, container, false)
        _view.findViewById<LoginButton>(R.id.containedButtonLoginFacebook).setOnClickListener { loginFacebook() }
        // Inflate the layout for this fragment
        return _view
    }

    private fun loginFacebook() {
        val instanceFirebase = LoginManager.getInstance()
        instanceFirebase.logInWithReadPermissions(this, listOf("email", "public_profile"))
        instanceFirebase.registerCallback(_callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val credential: AuthCredential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { saveLoggedIn() }
            }

            override fun onCancel() {
                Toast.makeText(context, "Cancelado!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        _callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view

        val hide = arguments?.getBoolean(HIDE_BACK)
        if (hide != null && hide) {
            hideBackIcon()
        }

        _view.findViewById<Button>(R.id.containedButtonLogin).setOnClickListener {
            saveLoggedIn()
        }
    }

    private fun saveLoggedIn() {
        val pref = _view.context.getSharedPreferences(
            MainActivity.MARVEL_APP,
            Context.MODE_PRIVATE
        )
        pref.edit().putBoolean(MainActivity.KEEP_LOGGED, true).apply()
        val intent = Intent(_view.context, FavoritesActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun hideBackIcon() {
        _view.findViewById<ImageView>(R.id.imgBackLogin).visibility = View.GONE
    }

    companion object {
        const val HIDE_BACK = "HIDE_BACK"
    }
}