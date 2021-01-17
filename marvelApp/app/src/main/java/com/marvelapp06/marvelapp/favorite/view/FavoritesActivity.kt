package com.marvelapp06.marvelapp.favorite.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.marvelapp06.marvelapp.LoginActivity
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.ProfileActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.login.view.LoginFragment

class FavoritesActivity : AppCompatActivity() {
    private lateinit var _auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        _auth = FirebaseAuth.getInstance()
        val currentUser = _auth.currentUser

        if (currentUser != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.favorites_nav_host_fragment, FavoriteFragment())
                .commit()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, LoginFragment.REQUEST_CODE)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.favoriteBottomNav)
        bottomNavigationView.selectedItemId = R.id.favorite

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginFragment.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val loggedIn = data!!.getBooleanExtra(LoginFragment.LOGGED_IN, false)
                if (loggedIn) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.favorites_nav_host_fragment, FavoriteFragment())
                        .commit()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.its_necessary_to_logged_to_access_favorites),
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.its_necessary_to_logged_to_access_favorites),
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}