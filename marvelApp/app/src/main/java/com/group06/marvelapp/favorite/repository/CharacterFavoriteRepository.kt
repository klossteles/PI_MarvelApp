package com.group06.marvelapp.favorite.repository

import android.content.Context
import com.group06.marvelapp.R
import com.group06.marvelapp.favorite.model.CharacterFavoriteModel

class CharacterFavoriteRepository(private val context: Context){
    fun getCharactersFavorites(callback:(charactersList:List<CharacterFavoriteModel>)->Unit){
        callback.invoke(
            listOf(
                CharacterFavoriteModel("Capitão América", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão América", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão América", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão América", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão América", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão América", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão América", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão América", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão América", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão América", R.drawable.capitaoamerica),
                CharacterFavoriteModel("Capitão América", R.drawable.capitaoamerica)
            )
        )
    }
}