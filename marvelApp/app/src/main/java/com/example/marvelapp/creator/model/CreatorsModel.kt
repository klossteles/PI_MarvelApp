package com.example.marvelapp.creator.model

import com.example.marvelapp.data.model.ThumbnailModel
import com.example.marvelapp.data.model.UrlModel
import com.example.marvelapp.comic.model.ComicsModel
import com.example.marvelapp.series.model.SeriesModel
import java.util.*

class CreatorsModel (
    id: Int?,
    firstName: String?,
    middleName: String?,
    lastName: String?,
    suffix: String?,
    fullName: String?,
    modified: Date?,
    resourceURI: String?,
    urls: List<UrlModel>?,
    thumbnail: ThumbnailModel?,
    series: List<SeriesModel>?,
    comics: List<ComicsModel>?
)