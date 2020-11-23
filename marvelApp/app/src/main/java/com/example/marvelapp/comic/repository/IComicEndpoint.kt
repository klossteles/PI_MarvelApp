package com.example.marvelapp.comic.repository

import com.example.marvelapp.comic.model.ComicsModel
import com.example.marvelapp.data.api.NetworkUtils
import com.example.marvelapp.data.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IComicEndpoint {
    @GET("/v1/public/comic")
    suspend fun getComic(@Query("titleStartsWith") titleStartsWith: String?, @Query("offset") offset: Int? = 0): ResponseModel<ComicsModel>
    @GET("/v1/public/comic/{comicId}")
    suspend fun getComicById(@Path("seriesId") seriesId: Int): ResponseModel<ComicsModel>

    companion object {
        val endpoint: IComicEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(IComicEndpoint::class.java)
        }
    }
}