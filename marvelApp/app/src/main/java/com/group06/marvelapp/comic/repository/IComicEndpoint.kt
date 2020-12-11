package com.group06.marvelapp.comic.repository

import com.group06.marvelapp.comic.model.ComicsModel
import com.group06.marvelapp.data.api.NetworkUtils
import com.group06.marvelapp.data.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IComicEndpoint {
    @GET("/v1/public/comics")
    suspend fun getComic(@Query("titleStartsWith") titleStartsWith: String?, @Query("offset") offset: Int? = 0): ResponseModel<ComicsModel>
    @GET("/v1/public/comics/{comicId}")
    suspend fun getComicById(@Path("comicId") comicId: Int): ResponseModel<ComicsModel>

    companion object {
        val endpoint: IComicEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(IComicEndpoint::class.java)
        }
    }
}