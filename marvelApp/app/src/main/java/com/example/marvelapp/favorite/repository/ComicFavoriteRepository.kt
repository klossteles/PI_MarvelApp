package com.example.marvelapp.favorite.repository

import android.content.Context
import com.example.marvelapp.R
import com.example.marvelapp.favorite.model.ComicFavoriteModel


class ComicFavoriteRepository (private val context: Context){
    fun getComicFavorites(callback:(seriesList:List<ComicFavoriteModel>)->Unit){
        callback.invoke(
            listOf(
                ComicFavoriteModel("Iron Man #2", R.drawable.capitaoamerica),
                ComicFavoriteModel("Iron Man #2", R.drawable.capitaoamerica),
                ComicFavoriteModel("Iron Man #2", R.drawable.capitaoamerica),
                ComicFavoriteModel("Iron Man #2", R.drawable.capitaoamerica),
                ComicFavoriteModel("Iron Man #2", R.drawable.capitaoamerica),
                ComicFavoriteModel("Iron Man #2", R.drawable.capitaoamerica),
                ComicFavoriteModel("Iron Man #2", R.drawable.capitaoamerica),
                ComicFavoriteModel("Iron Man #2", R.drawable.capitaoamerica),
                ComicFavoriteModel("Iron Man #2", R.drawable.capitaoamerica),
                ComicFavoriteModel("Iron Man #2", R.drawable.capitaoamerica),
                ComicFavoriteModel("Iron Man #2", R.drawable.capitaoamerica)
            )
        )
    }
}