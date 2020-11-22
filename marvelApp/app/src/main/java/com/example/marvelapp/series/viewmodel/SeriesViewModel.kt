package com.example.marvelapp.series.viewmodel

import androidx.lifecycle.*
import com.example.marvelapp.series.model.SeriesModel
import com.example.marvelapp.series.repository.SeriesRepository
import kotlinx.coroutines.Dispatchers

class SeriesViewModel (
    private val _repository: SeriesRepository
): ViewModel() {
    private var _series: List<SeriesModel> = listOf()
    private var _previousSeries = listOf<SeriesModel>()

    private var _totalPages: Int = 0
    private var _offset: Int = 0
    private var _count: Int = 0

    fun getList(title: String? = null) = liveData(Dispatchers.IO) {
        val response = _repository.getSeries(null)

        _count = response.data.count
        if (response.data.total != 0) {
            _totalPages = response.data.total / _count
        } else {
            _totalPages = 0
        }
        _series = response.data.results
        emit(response.data.results)
    }

    fun search(title: String? = null) = liveData(Dispatchers.IO) {
        if (_previousSeries.isEmpty()) {
            _previousSeries = _series
        }

        val response = _repository.getSeries(title)
        _count = response.data.count
        if (response.data.total != 0) {
            _totalPages = response.data.total / _count
        } else {
            _totalPages = 0
        }
        _offset = 0
        _series = response.data.results
        emit(response.data.results)
    }

    fun returnFirstList() =  _previousSeries.toMutableList()

    fun nextPage(title: String? = null) = liveData(Dispatchers.IO) {
        _previousSeries = _series
        if (_offset.plus(_count) <= _totalPages) {
            _offset = _offset.plus(_count)
            val response = _repository.getSeries(title, _offset)
            emit(response.data.results)
        }
    }

    fun getSeriesById(seriesId: Int) = liveData(Dispatchers.IO) {
        val response = _repository.getSeriesById(seriesId)
        emit(response.data.results[0])
    }

    class SeriesViewModelFactory(private val _repository: SeriesRepository ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SeriesViewModel(_repository) as T
        }
    }
}