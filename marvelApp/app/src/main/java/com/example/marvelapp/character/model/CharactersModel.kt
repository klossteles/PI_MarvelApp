package com.example.marvelapp.character.model

import com.example.marvelapp.data.model.ThumbnailModel
import com.example.marvelapp.data.model.UrlModel
import com.example.marvelapp.data.model.ComicsList
import com.example.marvelworld.api.models.SeriesList
import java.util.*

data class CharactersModel (
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: Date?,
    val resourceURI: String?,
    val urls: List<UrlModel>?,
    val thumbnail: ThumbnailModel?,
    val comics: ComicsList?,
    val series: SeriesList?
)