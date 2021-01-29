package com.marvelapp06.marvelapp.favorite.repository

import com.marvelapp06.marvelapp.favorite.dao.FavoriteDao
import com.marvelapp06.marvelapp.favorite.entity.FavoriteEntity

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    suspend fun addFavorite(favorite: FavoriteEntity) = favoriteDao.addFavorite(favorite)

    suspend fun getFavoritesCharacters(userId:String):List<FavoriteEntity> = favoriteDao.getFavoritesCharacters(userId)
    suspend fun getFavoritesSeries(userId:String):List<FavoriteEntity> = favoriteDao.getFavoritesSeries(userId)
    suspend fun getFavoritesComics(userId:String):List<FavoriteEntity> = favoriteDao.getFavoritesComics(userId)
    suspend fun getFavoritesCreators(userId:String):List<FavoriteEntity> = favoriteDao.getFavoritesCreators(userId)

    suspend fun deleteFavorite(modelId:Int,userId:String)=favoriteDao.deleteFavorite(modelId,userId)

    suspend  fun checkIfIsFavorite(modelId:Int,userId:String)=favoriteDao.checkIfIsFavorite(modelId,userId)

}