package com.group06.marvelapp.favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.group06.marvelapp.favorite.model.CharacterFavoriteModel
import com.group06.marvelapp.favorite.repository.CharacterFavoriteRepository

class CharacterFavoriteViewModel (
    private val repository: CharacterFavoriteRepository
):ViewModel(){

    val listCharactersFavoriteData = MutableLiveData<List<CharacterFavoriteModel>>()

    fun getCharactersFavorites(){
        repository.getCharactersFavorites {
            listCharactersFavoriteData.value = it
        }
    }

    class CharacterFavoriteViewModelFactory(
        private val repository: CharacterFavoriteRepository
    ):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterFavoriteViewModel(repository) as T
        }

    }
}