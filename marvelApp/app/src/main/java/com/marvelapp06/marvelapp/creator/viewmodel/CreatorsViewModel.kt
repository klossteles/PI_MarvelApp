package com.marvelapp06.marvelapp.creator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.marvelapp06.marvelapp.creator.model.CreatorsModel
import com.marvelapp06.marvelapp.creator.repository.CreatorsRepository
import kotlinx.coroutines.Dispatchers

class CreatorsViewModel(private val _repository : CreatorsRepository) : ViewModel() {

    private var _creators: List<CreatorsModel> = listOf()
    private var _previousCreators: List<CreatorsModel> = listOf()

    private var _totalPages: Int = 0
    private var _offset: Int = 0
    private var _count: Int = 0

    fun getListCreators(name: String? = null) = liveData(Dispatchers.IO) {
        val response = _repository.getCreators(null)
        _count = response.data.count
        _totalPages = if (response.data.total != 0) {
            response.data.total / _count
        } else {
            0
        }
        _creators = response.data.results
        emit(response.data.results)
    }

    fun searchCreator(name: String? = null) = liveData(Dispatchers.IO) {
        _previousCreators = _creators
        val response = _repository.getCreators(name)
        _count = response.data.count
        _totalPages = if (response.data.total != 0) {
            response.data.total / _count
        } else {
            0
        }
        emit(response.data.results)
    }

    fun returnFirstListCreators() = _previousCreators.toMutableList()

    fun nextPage(name: String? = null) = liveData(Dispatchers.IO) {
        if (_offset.plus(_count) < _totalPages) {
            _offset = _offset.plus(_count)
            val response = _repository.getCreators(name, _offset)
            emit(response.data.results)
        }
    }

    fun getCreatorsById(creatorId: Int) = liveData(Dispatchers.IO) {
        val response = _repository.getCreatorsById(creatorId)
        emit(response.data.results[0])
    }

    class CreatorsViewModelFactory(private val _repository: CreatorsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CreatorsViewModel(_repository) as T
        }
    }
}
