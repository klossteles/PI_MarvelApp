package com.marvelapp06.marvelapp.character.model

import com.marvelapp06.marvelapp.data.model.ThumbnailModel
import com.marvelapp06.marvelapp.data.model.UrlModel
import com.marvelapp06.marvelapp.data.model.ComicsList
import com.marvelapp06.marvelapp.data.model.SeriesList
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