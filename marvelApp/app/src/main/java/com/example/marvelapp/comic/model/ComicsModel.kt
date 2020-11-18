package com.example.marvelapp.comic.model

import com.example.marvelapp.character.model.CharactersModel
import com.example.marvelapp.creator.model.CreatorsModel
import com.example.marvelapp.data.model.ThumbnailModel
import com.example.marvelapp.data.model.UrlModel
import com.example.marvelapp.series.model.SeriesSummaryModel
import java.util.*

class ComicsModel (
    val id: Int?,
    val digitalId: Int?,
    val title: String?,
    val issueNumber: Double?,
    val variantDescription: String?,
    val description: String?,
    val modified: Date?,
    val isbn: String?,
    val upc: String?,
    val diamondCode: String?,
    val ean: String?,
    val issn: String?,
    val format: String?,
    val pageCount: Int?,
    val textObjects: List<TextObjectModel>?,
    val resoucerURI: String?,
    val urls: List<UrlModel>?,
    val series: SeriesSummaryModel?,
    val variants: List<ComicSummaryModel>?,
    val collections: List<ComicSummaryModel>?,
    val collectedIssues: List<ComicSummaryModel>?,
    val dates: List<ComicDate>?,
    val prices: List<ComicPrice>?,
    val thumbnail: ThumbnailModel?,
    val images: List<ThumbnailModel>?,
    val creators: List<CreatorsModel>?,
    val characters: List<CharactersModel?>
)