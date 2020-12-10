package com.example.marvelapp.favorite.repository

import android.content.Context
import com.example.marvelapp.R
import com.example.marvelapp.favorite.model.CreatorsFavoriteModel


class CreatorsFavoriteRepository (private val context: Context){
    fun getCreatorsFavorites(callback:(creatorsList:List<CreatorsFavoriteModel>)->Unit){
        callback.invoke(
            listOf(
                CreatorsFavoriteModel("Stan Lee", R.drawable.stanlee),
                CreatorsFavoriteModel("Stan Lee", R.drawable.stanlee),
                CreatorsFavoriteModel("Stan Lee", R.drawable.stanlee),
                CreatorsFavoriteModel("Stan Lee", R.drawable.stanlee),
                CreatorsFavoriteModel("Stan Lee", R.drawable.stanlee),
                CreatorsFavoriteModel("Stan Lee", R.drawable.stanlee),
                CreatorsFavoriteModel("Stan Lee", R.drawable.stanlee),
                CreatorsFavoriteModel("Stan Lee", R.drawable.stanlee),
                CreatorsFavoriteModel("Stan Lee", R.drawable.stanlee),
                CreatorsFavoriteModel("Stan Lee", R.drawable.stanlee),
                CreatorsFavoriteModel("Stan Lee", R.drawable.stanlee)
            )
        )
    }
}