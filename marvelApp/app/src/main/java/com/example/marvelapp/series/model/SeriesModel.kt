package com.example.marvelapp.series.model

import com.example.marvelapp.data.model.*

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
    val comics: ComicsList?,
    val characters: CharactersList?,
    val creators: CreatorsList?,
    val next: SeriesSummaryModel?,
    val previous: SeriesSummaryModel?
)