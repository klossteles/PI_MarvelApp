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

    @Query("SELECT * FROM Favorite WHERE category=1 and userId=:userId")
    suspend fun getFavoritesCharacters(userId:String):List<FavoriteEntity>

    @Query("SELECT * FROM Favorite WHERE category=2 and userId=:userId")
    suspend fun getFavoritesSeries(userId:String):List<FavoriteEntity>

    @Query("SELECT * FROM Favorite WHERE category=3 and userId=:userId ")
    suspend fun getFavoritesComics(userId:String):List<FavoriteEntity>

    @Query("SELECT * FROM Favorite WHERE category=4 and userId=:userId ")
    suspend fun getFavoritesCreators(userId:String):List<FavoriteEntity>

    @Query("SELECT * FROM FAVORITE WHERE modelId=:modelId and userId=:userId")
    suspend  fun checkIfIsFavorite(modelId:Int,userId:String):List<FavoriteEntity>


    @Transaction
    @Query("DELETE FROM Favorite where modelId=:modelId and userId=:userId")
    suspend  fun deleteFavorite(modelId:Int, userId:String)
}