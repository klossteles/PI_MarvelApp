package com.group06.marvelapp.character.repository

import com.group06.marvelapp.character.model.CharactersModel
import com.group06.marvelapp.data.api.NetworkUtils
import com.group06.marvelapp.data.model.ResponseModel
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