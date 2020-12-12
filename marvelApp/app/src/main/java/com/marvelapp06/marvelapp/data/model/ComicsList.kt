package com.marvelapp06.marvelapp.data.model

data class ComicsList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<ComicSummary>
)