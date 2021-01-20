package com.marvelapp06.marvelapp.favorite.repository

import com.marvelapp06.marvelapp.favorite.dao.FavoriteDao
import com.marvelapp06.marvelapp.favorite.entity.FavoriteEntity

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    suspend fun addFavorite(favorite: FavoriteEntity) = favoriteDao.addFavorite(favorite)

    suspend fun getFavoritesCharacters():List<FavoriteEntity> = favoriteDao.getFavoritesCharacters()

    suspend fun deleteFavorite(modelId:Int)=favoriteDao.deleteFavorite(modelId)

    suspend  fun checkIfIsFavorite(modelId:Int)=favoriteDao.checkIfIsFavorite(modelId)

}