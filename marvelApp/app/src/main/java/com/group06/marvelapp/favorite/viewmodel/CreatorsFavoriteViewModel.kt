package com.group06.marvelapp.favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.group06.marvelapp.favorite.model.CreatorsFavoriteModel
import com.group06.marvelapp.favorite.repository.CreatorsFavoriteRepository


class CreatorsFavoriteViewModel(
    private val repository: CreatorsFavoriteRepository
) : ViewModel() {

    val listCreatorsFavoriteData = MutableLiveData<List<CreatorsFavoriteModel>>()

    fun getCreatorsFavorites() {
        repository.getCreatorsFavorites {
            listCreatorsFavoriteData.value = it
        }
    }

    class CreatorsFavoriteViewModelFactory(
        private val repository: CreatorsFavoriteRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CreatorsFavoriteViewModel(repository) as T
        }

    }
}