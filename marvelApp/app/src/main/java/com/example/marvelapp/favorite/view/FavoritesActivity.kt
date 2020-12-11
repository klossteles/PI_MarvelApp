package com.example.marvelapp.favorite.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.marvelapp.MainActivity
import com.example.marvelapp.ProfileActivity
import com.example.marvelapp.R
import com.example.marvelapp.login.view.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportFragmentManager.beginTransaction()
            .replace(R.id.favoriteFrameLayout, FavoriteFragment())
            .commit()

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