package com.group06.marvelapp.series.repository

import com.group06.marvelapp.data.api.NetworkUtils
import com.group06.marvelapp.data.model.ResponseModel
import com.group06.marvelapp.series.model.SeriesModel
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