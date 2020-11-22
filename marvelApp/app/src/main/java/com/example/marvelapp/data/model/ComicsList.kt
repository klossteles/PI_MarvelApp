package com.example.marvelapp.data.model

import com.example.marvelworld.api.models.ComicSummary

data class ComicsList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<ComicSummary>
)