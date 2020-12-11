package com.marvelapp06.marvelapp.favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marvelapp06.marvelapp.favorite.model.CreatorsFavoriteModel
import com.marvelapp06.marvelapp.favorite.repository.CreatorsFavoriteRepository

class CreatorsFavoriteViewModel(
    private val _repository: CreatorsFavoriteRepository
) : ViewModel() {

    val listCreatorsFavoriteData = MutableLiveData<List<CreatorsFavoriteModel>>()

    fun getCreatorsFavorites() {
        _repository.getCreatorsFavorites {
            listCreatorsFavoriteData.value = it
        }
    }

    class CreatorsFavoriteViewModelFactory(
        private val _repository: CreatorsFavoriteRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CreatorsFavoriteViewModel(_repository) as T
        }

    }
}