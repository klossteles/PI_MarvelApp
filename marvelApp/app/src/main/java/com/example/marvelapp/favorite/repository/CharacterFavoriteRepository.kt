package com.example.marvelapp.favorite.repository

import android.content.Context
import com.example.marvelapp.R
import com.example.marvelapp.favorite.model.CharacterFavoriteModel

class CharacterFavoriteRepository(private val context: Context){
    fun getCharactersFavorites(callback:(charactersList:List<CharacterFavoriteModel>)->Unit){
        callback.invoke(
            listOf(
                CharacterFavoriteModel("Capitão", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão", R.drawable.capitaoamerica)
            )
        )
    }
}