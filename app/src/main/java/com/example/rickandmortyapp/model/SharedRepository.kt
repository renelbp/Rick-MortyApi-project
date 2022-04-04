package com.example.rickandmortyapp.model

import com.example.rickandmortyapp.domain.Character
import com.example.rickandmortyapp.domain.mappers.CharacterMapper
import com.example.rickandmortyapp.model.network.GetEpisodeByIdResponse
import com.example.rickandmortyapp.model.network.NetworkLayer
import com.example.rickandmortyapp.model.network.response.GetCharacterByIdResponse

class SharedRepository {
    suspend fun getCharacterById(characterId: Int): Character?{
        val request = NetworkLayer.apiClient.getCharacterById(characterId)
        if (request.failed || !request.isSuccesful){
            return null
        }

        val networkEpisodes = getEpisodeFromCharacterResponse(request.body)
        return CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisodes
        )
    }

    private suspend fun getEpisodeFromCharacterResponse(
        characterResponse: GetCharacterByIdResponse
    ): List<GetEpisodeByIdResponse>{
        val episodeRange = characterResponse.episode.map {
            it.substring(it.lastIndexOf("/")+1)
        }.toString()
        val request = NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        if (request.failed || !request.isSuccesful){
            return emptyList()
        }
        return request.body
    }
}
