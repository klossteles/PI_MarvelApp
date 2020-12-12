package com.marvelapp06.marvelapp.creator.repository

class CreatorsRepository {

    private val _client = ICreatorsEndpoint.endpoint
    suspend fun getCreators(name : String?, offset: Int?=0) = _client.getCreators(name, offset)
    suspend fun getCreatorsById (creatorId : Int) = _client.getCreatorById(creatorId)
}