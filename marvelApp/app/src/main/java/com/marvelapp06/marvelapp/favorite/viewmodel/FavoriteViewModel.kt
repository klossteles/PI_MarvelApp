package com.marvelapp06.marvelapp.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.marvelapp06.marvelapp.favorite.entity.FavoriteEntity
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    fun addFavorite(userId: String, modelId: Int, favorite: String, category: Int) = liveData(Dispatchers.IO) {
        val f = FavoriteEntity(0, userId,modelId, favorite, category)
        repository.addFavorite(FavoriteEntity(0,userId, modelId, favorite, category))

        emit(f)
    }

    fun getFavoritesCharacters(userId:String) = liveData(Dispatchers.IO) {
        emit(repository.getFavoritesCharacters(userId))
    }

    fun getFavoritesSeries(userId:String) = liveData(Dispatchers.IO) {
        emit(repository.getFavoritesSeries(userId))
    }

    fun getFavoritesComics(userId:String) = liveData(Dispatchers.IO) {
        emit(repository.getFavoritesComics(userId))
    }

    fun getFavoritesCreators(userId:String) = liveData(Dispatchers.IO) {
        emit(repository.getFavoritesCreators(userId))
    }


    fun deleteFavorite(userId:String,modelId: Int) = liveData(Dispatchers.IO) {
        repository.deleteFavorite(modelId,userId)
        emit(true)
    }

    fun checkIfIsFavorite(userId:String,modelId: Int) = liveData(Dispatchers.IO) {
        emit(repository.checkIfIsFavorite(modelId,userId))
    }

    class FavoriteViewModelFactory(private val repository: FavoriteRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoriteViewModel(repository) as T
        }
    }
}