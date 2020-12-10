package com.example.marvelapp.favorite.repository

import android.content.Context
import com.example.marvelapp.R
import com.example.marvelapp.favorite.model.SeriesFavoriteModel


class SeriesFavoriteRepository (private val context: Context){
    fun getSeriesFavorites(callback:(seriesList:List<SeriesFavoriteModel>)->Unit){
        callback.invoke(
            listOf(
                SeriesFavoriteModel("Wolverine", R.drawable.i_wolverine),
                SeriesFavoriteModel("Wolverine", R.drawable.i_wolverine),
                SeriesFavoriteModel("Wolverine", R.drawable.i_wolverine),
                SeriesFavoriteModel("Wolverine", R.drawable.i_wolverine),
                SeriesFavoriteModel("Wolverine", R.drawable.i_wolverine),
                SeriesFavoriteModel("Wolverine", R.drawable.i_wolverine),
                SeriesFavoriteModel("Wolverine", R.drawable.i_wolverine),
                SeriesFavoriteModel("Wolverine", R.drawable.i_wolverine),
                SeriesFavoriteModel("Wolverine", R.drawable.i_wolverine),
                SeriesFavoriteModel("Wolverine", R.drawable.i_wolverine),
                SeriesFavoriteModel("Wolverine", R.drawable.i_wolverine)
            )
        )
    }
}