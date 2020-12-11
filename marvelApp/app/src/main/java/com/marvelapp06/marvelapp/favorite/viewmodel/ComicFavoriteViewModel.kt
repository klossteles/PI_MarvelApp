package com.marvelapp06.marvelapp.favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marvelapp06.marvelapp.favorite.model.ComicFavoriteModel
import com.marvelapp06.marvelapp.favorite.repository.ComicFavoriteRepository

class ComicFavoriteViewModel(
    private val _repository: ComicFavoriteRepository
) : ViewModel() {

    val listComicFavoriteData = MutableLiveData<List<ComicFavoriteModel>>()

    fun getComicFavorites() {
        _repository.getComicFavorites {
            listComicFavoriteData.value = it
        }
    }

    class ComicFavoriteViewModelFactory(
        private val _repository: ComicFavoriteRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicFavoriteViewModel(_repository) as T
        }

    }
}