package com.marvelapp06.marvelapp.character.viewmodel

import androidx.lifecycle.*
import com.marvelapp06.marvelapp.character.model.CharactersModel
import com.marvelapp06.marvelapp.character.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers

class CharacterViewModel(
    private val _repository: CharacterRepository
) : ViewModel() {
    private var _characters: List<CharactersModel> = listOf()
    private var _previousCharacters: List<CharactersModel> = listOf()

    private var _totalPages: Int = 0
    private var _offset: Int = 0
    private var _count: Int = 0

    fun getListCharacters(name: String? = null) = liveData(Dispatchers.IO) {
        val response = _repository.getCharacters(null)
        _count = response.data.count
        _totalPages = if (response.data.total != 0) {
            response.data.total / _count
        } else {
            0
        }
        _characters = response.data.results
        emit(response.data.results)
    }

    fun searchCharacter(name: String? = null) = liveData(Dispatchers.IO) {
        _previousCharacters = _characters
        val response = _repository.getCharacters(name)
        _count = response.data.count
        _totalPages = if (response.data.total != 0) {
            response.data.total / _count
        } else {
            0
        }
        emit(response.data.results)
    }

    fun returnFirstListCharacters() = _previousCharacters.toMutableList()

    fun nextPage(name: String? = null) = liveData(Dispatchers.IO) {
        if (_offset.plus(_count) <= _totalPages) {
            _offset = _offset.plus(_count)
            val response = _repository.getCharacters(name, _offset)
            emit(response.data.results)
        }
    }

    fun getCharactersById(characterId: Int) = liveData(Dispatchers.IO) {
        val response = _repository.getCharacterById(characterId)
        emit(response.data.results[0])
    }

    class CharacterViewModelFactory(private val _repository: CharacterRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterViewModel(_repository) as T
        }
    }
}