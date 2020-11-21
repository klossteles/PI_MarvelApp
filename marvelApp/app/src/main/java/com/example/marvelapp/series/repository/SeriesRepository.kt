package com.example.marvelapp.series.repository

class SeriesRepository() {
    private val _client = ISeriesEndpoint.endpoint
    suspend fun getSeries(title: String?, offset: Int? = 0) = _client.getSeries(title, offset)
    suspend fun getSeriesById(seriesId: Int) = _client.getSeriesById(seriesId)
}