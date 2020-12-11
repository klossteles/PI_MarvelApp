package com.group06.marvelapp.creator.repository

import com.group06.marvelapp.creator.model.CreatorsModel
import com.group06.marvelapp.data.api.NetworkUtils
import com.group06.marvelapp.data.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ICreatorsEndpoint {
    @GET("/v1/public/creators")
    suspend fun getCreators (@Query("nameStartsWith") nameStartsWith: String?,
                             @Query("offset") offset: Int? = 0
    ) : ResponseModel<CreatorsModel>

    @GET("/v1/public/creators/{creatorId}")
    suspend fun getCreatorById (@Path("creatorId") creatorId : Int
    ): ResponseModel<CreatorsModel>

    companion object{
        val endpoint : ICreatorsEndpoint by lazy{
            NetworkUtils.getRetrofitInstance().create(ICreatorsEndpoint::class.java)

        }
    }
}