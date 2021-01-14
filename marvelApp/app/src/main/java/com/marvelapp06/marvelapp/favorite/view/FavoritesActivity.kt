package com.marvelapp06.marvelapp.favorite.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.ProfileActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.login.view.LoginFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marvelapp06.marvelapp.LoginActivity

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val pref = this.getSharedPreferences(MainActivity.MARVEL_APP, Context.MODE_PRIVATE)
        val value = pref.getBoolean(MainActivity.KEEP_LOGGED, false)

        if (!value) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(LoginFragment.HIDE_BACK, true)
            intent.putExtra(LoginFragment.CALLER, LoginFragment.FAVORITE_CALLER)
            startActivity(intent)
            finish()
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
                R.id.favorite -> {
                    val intent = Intent(this, FavoritesActivity::class.java)
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
}