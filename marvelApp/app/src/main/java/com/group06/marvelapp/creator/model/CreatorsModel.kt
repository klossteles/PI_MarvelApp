package com.group06.marvelapp.creator.model

import com.group06.marvelapp.data.model.ThumbnailModel
import com.group06.marvelapp.data.model.UrlModel
import com.group06.marvelapp.data.model.ComicsList
import com.group06.marvelapp.data.model.EventList
import com.group06.marvelapp.data.model.SeriesList
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