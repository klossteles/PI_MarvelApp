package com.example.marvelapp.character.repository

import com.example.marvelapp.character.model.CharactersModel
import com.example.marvelapp.data.api.NetworkUtils
import com.example.marvelapp.data.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ICharacterEndpoint {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("nameStartsWith") nameStartsWith: String?,
        @Query("offset") offset: Int? = 0
    ): ResponseModel<CharactersModel>

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterById(@Path("characterId") characterId: Int): ResponseModel<CharactersModel>


    companion object {
        val endpoint: ICharacterEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(ICharacterEndpoint::class.java)
        }
    }

}