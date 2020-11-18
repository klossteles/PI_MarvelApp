package com.example.marvelapp.character.model

import com.example.marvelapp.data.model.ThumbnailModel
import com.example.marvelapp.data.model.UrlModel
import com.example.marvelapp.comic.model.ComicsModel
import com.example.marvelapp.series.model.SeriesModel
import java.util.*

data class CharactersModel (
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: Date?,
    val resourceURI: String?,
    val urls: List<UrlModel>?,
    val thumbnail: ThumbnailModel?,
    val comics: List<ComicsModel>?,
    val series: List<SeriesModel>?
)