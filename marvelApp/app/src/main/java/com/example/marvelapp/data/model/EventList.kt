package com.example.marvelapp.data.model

import com.example.marvelworld.api.models.EventSummary

data class EventList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<EventSummary>
)