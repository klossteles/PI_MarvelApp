package com.example.marvelapp.series.viewmodel

import androidx.lifecycle.*
import com.example.marvelapp.series.model.SeriesModel
import com.example.marvelapp.series.repository.SeriesRepository
import kotlinx.coroutines.Dispatchers

class SeriesViewModel (
    private val _repository: SeriesRepository
): ViewModel() {
    private var _series: List<SeriesModel> = listOf()
    private var _firstList = listOf<SeriesModel>()

    fun getList(title: String? = null) = liveData(Dispatchers.IO) {
        val response = _repository.getSeries(null)

        _series = response.data.results
        emit(response.data.results)
    }

    fun search(title: String? = null) = liveData(Dispatchers.IO) {
        if (_firstList.isEmpty()) {
            _firstList = _series
        }

        val response = _repository.getSeries(title)
        _series = response.data.results
        emit(response.data.results)
    }

    fun returnFirstList() =  _firstList.toMutableList()

    class SeriesViewModelFactory(private val _repository: SeriesRepository ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SeriesViewModel(_repository) as T
        }
    }
}