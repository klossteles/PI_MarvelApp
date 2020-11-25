package com.example.marvelapp.comic.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.marvelapp.comic.model.ComicsModel
import com.example.marvelapp.comic.repository.ComicRepository
import kotlinx.coroutines.Dispatchers

class ComicViewModel (
    private val _repository: ComicRepository
): ViewModel() {
    private var _comic: List<ComicsModel> = listOf()
    private var _previousComic = listOf<ComicsModel>()
    private var _totalPages: Int = 0
    private var _offset: Int = 0
    private var _count: Int = 0

    fun getList(title: String? = null) = liveData(Dispatchers.IO) {
        val response = _repository.getComic(null)

        _count = response.data.count
        if (response.data.total != 0) {
            _totalPages = response.data.total / _count
        } else {
            _totalPages = 0
        }
        _comic = response.data.results
        emit(response.data.results)
    }

    fun search(title: String? = null) = liveData(Dispatchers.IO) {
        if (_previousComic.isEmpty()) {
            _previousComic = _comic
        }
        val response = _repository.getComic(title)
        _count = response.data.count
        if (response.data.total != 0) {
            _totalPages = response.data.total / _count
        } else {
            _totalPages = 0
        }
        _offset = 0
        _comic = response.data.results
        emit(response.data.results)
    }

    fun returnFirstList() = _previousComic.toMutableList()

    fun nextPage(title: String? = null) = liveData(Dispatchers.IO) {
        _previousComic = _comic
        if (_offset.plus(_count) <= _totalPages) {
            _offset = _offset.plus(_count)
            val response = _repository.getComic(title, _offset)
            emit(response.data.results)
        }
    }

    fun getComicById(comicId: Int) = liveData(Dispatchers.IO) {
        val response = _repository.getComicById(comicId)
        emit(response.data.results[0])
    }

    class ComicViewModelFactory(private val _repository: ComicRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicViewModel(_repository) as T
        }
    }
}