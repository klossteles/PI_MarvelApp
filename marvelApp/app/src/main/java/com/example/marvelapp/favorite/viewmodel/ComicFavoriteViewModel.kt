package com.example.marvelapp.favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvelapp.favorite.model.ComicFavoriteModel
import com.example.marvelapp.favorite.repository.ComicFavoriteRepository


class ComicFavoriteViewModel(
    private val repository: ComicFavoriteRepository
) : ViewModel() {

    val listComicFavoriteData = MutableLiveData<List<ComicFavoriteModel>>()

    fun getComicFavorites() {
        repository.getComicFavorites {
            listComicFavoriteData.value = it
        }
    }

    class ComicFavoriteViewModelFactory(
        private val repository: ComicFavoriteRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicFavoriteViewModel(repository) as T
        }

    }
}