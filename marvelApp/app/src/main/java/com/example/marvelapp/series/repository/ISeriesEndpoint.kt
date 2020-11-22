package com.example.marvelapp.series.repository

import com.example.marvelapp.data.api.NetworkUtils
import com.example.marvelapp.data.model.ResponseModel
import com.example.marvelapp.series.model.SeriesModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ISeriesEndpoint {
    @GET("/v1/public/series")
    suspend fun getSeries(@Query("titleStartsWith") titleStartsWith: String?, @Query("offset") offset: Int? = 0): ResponseModel<SeriesModel>
    @GET("/v1/public/series/{seriesId}")
    suspend fun getSeriesById(@Path("seriesId") seriesId: Int): ResponseModel<SeriesModel>

    companion object {
        val endpoint: ISeriesEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(ISeriesEndpoint::class.java)
        }
    }
}