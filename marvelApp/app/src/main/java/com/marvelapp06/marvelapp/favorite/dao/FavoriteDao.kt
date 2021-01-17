package com.marvelapp06.marvelapp.favorite.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marvelapp06.marvelapp.favorite.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addFavorite(favorite:FavoriteEntity)

    @Query("SELECT * FROM Favorite")
    suspend fun getFavoritesCharacters():List<FavoriteEntity>
}