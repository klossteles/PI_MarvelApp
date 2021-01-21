package com.marvelapp06.marvelapp.favorite.repository

import com.marvelapp06.marvelapp.favorite.dao.FavoriteDao
import com.marvelapp06.marvelapp.favorite.entity.FavoriteEntity

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    suspend fun addFavorite(favorite: FavoriteEntity) = favoriteDao.addFavorite(favorite)

    suspend fun getFavoritesCharacters():List<FavoriteEntity> = favoriteDao.getFavoritesCharacters()
    suspend fun getFavoritesSeries():List<FavoriteEntity> = favoriteDao.getFavoritesSeries()
    suspend fun getFavoritesComics():List<FavoriteEntity> = favoriteDao.getFavoritesComics()
    suspend fun getFavoritesCreators():List<FavoriteEntity> = favoriteDao.getFavoritesCreators()

    suspend fun deleteFavorite(modelId:Int)=favoriteDao.deleteFavorite(modelId)

    suspend  fun checkIfIsFavorite(modelId:Int)=favoriteDao.checkIfIsFavorite(modelId)

}