package com.marvelapp06.marvelapp.favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marvelapp06.marvelapp.favorite.model.CharacterFavoriteModel
import com.marvelapp06.marvelapp.favorite.repository.CharacterFavoriteRepository

class CharacterFavoriteViewModel (
    private val _repository: CharacterFavoriteRepository
):ViewModel(){

    val listCharactersFavoriteData = MutableLiveData<List<CharacterFavoriteModel>>()

    fun getCharactersFavorites(){
        _repository.getCharactersFavorites {
            listCharactersFavoriteData.value = it
        }
    }

    class CharacterFavoriteViewModelFactory(
        private val _repository: CharacterFavoriteRepository
    ):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterFavoriteViewModel(_repository) as T
        }

    }
}