package com.group06.marvelapp.character.repository

class CharacterRepository {
    private val _client=ICharacterEndpoint.endpoint
    suspend fun getCharacters(name:String?,offset:Int?=0)=_client.getCharacters(name,offset)
    suspend fun getCharacterById(characterId:Int)=_client.getCharacterById(characterId)
}