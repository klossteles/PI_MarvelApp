package com.group06.marvelapp.data.model

data class CharactersList (
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<CharacterSummary>
)