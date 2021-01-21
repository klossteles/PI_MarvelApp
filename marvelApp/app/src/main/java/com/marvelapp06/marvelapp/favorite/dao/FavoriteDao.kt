package com.marvelapp06.marvelapp.favorite.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.marvelapp06.marvelapp.favorite.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addFavorite(favorite:FavoriteEntity)

    @Query("SELECT * FROM Favorite WHERE category=1")
    suspend fun getFavoritesCharacters():List<FavoriteEntity>

    @Query("SELECT * FROM Favorite WHERE category=2")
    suspend fun getFavoritesSeries():List<FavoriteEntity>

    @Query("SELECT * FROM Favorite WHERE category=3")
    suspend fun getFavoritesComics():List<FavoriteEntity>

    @Query("SELECT * FROM Favorite WHERE category=4")
    suspend fun getFavoritesCreators():List<FavoriteEntity>



    @Query("SELECT * FROM FAVORITE WHERE modelId=:modelId")
    suspend  fun checkIfIsFavorite(modelId:Int):List<FavoriteEntity>

    @Transaction
    @Query("DELETE FROM Favorite where modelId=:modelId")
    suspend  fun deleteFavorite(modelId:Int)
}