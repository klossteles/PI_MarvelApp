package com.example.marvelapp.favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvelapp.favorite.model.SeriesFavoriteModel
import com.example.marvelapp.favorite.repository.SeriesFavoriteRepository

class SeriesFavoriteViewModel (
    private val repository: SeriesFavoriteRepository
): ViewModel(){

    val listSeriesFavoriteData = MutableLiveData<List<SeriesFavoriteModel>>()

    fun getSeriesFavorites(){
        repository.getSeriesFavorites {
            listSeriesFavoriteData.value = it
        }
    }

    class SeriesFavoriteViewModelFactory(
        private val repository: SeriesFavoriteRepository
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SeriesFavoriteViewModel(repository) as T
        }

    }}