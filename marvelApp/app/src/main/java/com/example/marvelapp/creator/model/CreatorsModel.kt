package com.example.marvelapp.creator.model

import com.example.marvelapp.data.model.ThumbnailModel
import com.example.marvelapp.data.model.UrlModel
import com.example.marvelapp.comic.model.ComicsModel
import com.example.marvelapp.series.model.SeriesModel
import java.util.*

class CreatorsModel (
    val id: Int?,
    val firstName: String?,
    val middleName: String?,
    val lastName: String?,
    val suffix: String?,
    val fullName: String?,
    val modified: Date?,
    val resourceURI: String?,
    val urls: List<UrlModel>?,
    val  thumbnail: ThumbnailModel?,
    val series: List<SeriesModel>?,
    val comics: List<ComicsModel>?
)