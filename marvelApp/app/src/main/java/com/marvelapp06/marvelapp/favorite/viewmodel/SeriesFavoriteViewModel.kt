package com.marvelapp06.marvelapp.favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marvelapp06.marvelapp.favorite.model.SeriesFavoriteModel
import com.marvelapp06.marvelapp.favorite.repository.SeriesFavoriteRepository

class SeriesFavoriteViewModel (
    private val _repository: SeriesFavoriteRepository
): ViewModel(){

    val listSeriesFavoriteData = MutableLiveData<List<SeriesFavoriteModel>>()

    fun getSeriesFavorites(){
        _repository.getSeriesFavorites {
            listSeriesFavoriteData.value = it
        }
    }

    class SeriesFavoriteViewModelFactory(
        private val _repository: SeriesFavoriteRepository
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SeriesFavoriteViewModel(_repository) as T
        }

    }}