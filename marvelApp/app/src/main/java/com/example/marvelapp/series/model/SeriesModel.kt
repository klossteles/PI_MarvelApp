package com.example.marvelapp.series.model

import com.example.marvelapp.character.model.CharactersModel
import com.example.marvelapp.creator.model.CreatorsModel
import com.example.marvelapp.data.model.ThumbnailModel
import com.example.marvelapp.data.model.UrlModel
import com.example.marvelapp.comic.model.ComicsModel

data class SeriesModel (
    val id: Int?,
    val title: String?,
    val description: String?,
    val urls: List<UrlModel>?,
    val startYear: Int?,
    val endYear: Int?,
    val rating: String?,
    val type: String?,
    val modified: String?,
    val thumbnail: ThumbnailModel?,
    val comics: List<ComicsModel>,
    val characters: List<CharactersModel>?,
    val creators: List<CreatorsModel>?,
    val next: SeriesSummaryModel?,
    val previous: SeriesSummaryModel?
)