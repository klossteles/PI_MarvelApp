package com.marvelapp06.marvelapp.favorite.repository

import android.content.Context
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.model.ComicFavoriteModel

class ComicFavoriteRepository (private val context: Context){
    fun getComicFavorites(callback:(seriesList:List<ComicFavoriteModel>)->Unit){
        callback.invoke(
            listOf(
                ComicFavoriteModel("Iron Man #2", R.drawable.ironman),
                ComicFavoriteModel("Iron Man #2", R.drawable.ironman),
                ComicFavoriteModel("Iron Man #2", R.drawable.ironman),
                ComicFavoriteModel("Iron Man #2", R.drawable.ironman),
                ComicFavoriteModel("Iron Man #2", R.drawable.ironman),
                ComicFavoriteModel("Iron Man #2", R.drawable.ironman),
                ComicFavoriteModel("Iron Man #2", R.drawable.ironman),
                ComicFavoriteModel("Iron Man #2", R.drawable.ironman),
                ComicFavoriteModel("Iron Man #2", R.drawable.ironman),
                ComicFavoriteModel("Iron Man #2", R.drawable.ironman),
                ComicFavoriteModel("Iron Man #2", R.drawable.ironman)
            )
        )
    }
}