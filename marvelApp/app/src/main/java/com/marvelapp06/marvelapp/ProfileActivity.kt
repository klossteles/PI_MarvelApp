package com.marvelapp06.marvelapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.login.Login
import com.marvelapp06.marvelapp.favorite.view.FavoritesActivity
import com.marvelapp06.marvelapp.login.view.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.marvelapp06.marvelapp.favorite.view.FavoriteFragment
import com.marvelapp06.marvelapp.login.view.LoginFragment

class ProfileActivity : AppCompatActivity() {
    private lateinit var _auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        _auth = FirebaseAuth.getInstance()
        val currentUser = _auth.currentUser

        if (currentUser != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.profile_nav_host_fragment, ProfileFragment())
                .commit()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView =  findViewById<BottomNavigationView>(R.id.profileBottomNav)
        bottomNavigationView.selectedItemId = R.id.profile

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.favorite -> {
                    val intent = Intent(this, FavoritesActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("PROFILE_RESUME", "PROFILE RESUME")
        val currentUser = _auth.currentUser
        if (currentUser != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.favorites_nav_host_fragment, FavoriteFragment())
                .commit()
        } else {
            Toast.makeText(this, getString(R.string.its_necessary_to_logged_to_access_profile), Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}