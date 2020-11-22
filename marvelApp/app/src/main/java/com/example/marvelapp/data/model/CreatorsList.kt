package com.example.marvelapp.data.model

import com.example.marvelworld.api.models.CreatorSummary

data class CreatorsList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<CreatorSummary>
)