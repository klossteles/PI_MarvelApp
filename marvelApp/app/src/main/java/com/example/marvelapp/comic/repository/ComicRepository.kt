package com.example.marvelapp.comic.repository


class ComicRepository () {
    private val _client = IComicEndpoint.endpoint
    suspend fun getComic(title: String?, offset: Int? = 0) = _client.getComic(title, offset)
    suspend fun getComicById(comicId: Int) = _client.getComicById(comicId)
}
