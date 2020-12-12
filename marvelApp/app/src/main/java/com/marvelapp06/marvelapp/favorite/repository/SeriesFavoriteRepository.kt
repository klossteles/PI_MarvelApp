package com.marvelapp06.marvelapp.favorite.repository

import android.content.Context
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.model.SeriesFavoriteModel

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