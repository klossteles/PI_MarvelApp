package com.marvelapp06.marvelapp.creator.model

import com.marvelapp06.marvelapp.data.model.ThumbnailModel
import com.marvelapp06.marvelapp.data.model.UrlModel
import com.marvelapp06.marvelapp.data.model.ComicsList
import com.marvelapp06.marvelapp.data.model.EventList
import com.marvelapp06.marvelapp.data.model.SeriesList
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
    val thumbnail: ThumbnailModel?,
    val series: SeriesList?,
    val comics: ComicsList?,
    val events: EventList?
)