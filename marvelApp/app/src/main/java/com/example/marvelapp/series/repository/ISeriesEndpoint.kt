package com.example.marvelapp.series.repository

import com.example.marvelapp.data.api.NetworkUtils
import com.example.marvelapp.data.model.ResponseModel
import com.example.marvelapp.series.model.SeriesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ISeriesEndpoint {
    @GET("/v1/public/series")
    suspend fun getSeries(@Query("title") title: String?, @Query("offset") offset: Int? = 0): ResponseModel<SeriesModel>

    companion object {
        val endpoint: ISeriesEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(ISeriesEndpoint::class.java)
        }
    }
}