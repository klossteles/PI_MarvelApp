package com.example.marvelapp.favorite.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marvelapp.R

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentFavorites, FavoriteFragment())
            .commit()

    }
}