package com.example.marvelapp.series.repository

class SeriesRepository() {
    private val _client = ISeriesEndpoint.endpoint
    suspend fun getSeries(title: String?) = _client.getSeries(title)
}