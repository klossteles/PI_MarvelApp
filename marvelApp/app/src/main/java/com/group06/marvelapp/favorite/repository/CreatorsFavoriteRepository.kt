package com.group06.marvelapp.favorite.repository

import android.content.Context
import com.group06.marvelapp.R
import com.group06.marvelapp.favorite.model.CreatorsFavoriteModel


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