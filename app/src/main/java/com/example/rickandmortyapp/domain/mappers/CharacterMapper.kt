package com.example.rickandmortyapp.domain.mappers

import com.example.rickandmortyapp.domain.Character
import com.example.rickandmortyapp.model.network.GetEpisodeByIdResponse
import com.example.rickandmortyapp.model.network.response.GetCharacterByIdResponse

object CharacterMapper {
    fun buildFrom(response: GetCharacterByIdResponse,
    episodes: List<GetEpisodeByIdResponse>): Character{
        return Character(
            episodeList = episodes.map{
              EpisodeMapper.buildFrom(it)
            },
        gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(
                name = response.name,
                url  = response.location.url
            ),
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.url
            ),
            species = response.species,
            status = response.status
        )
    }

}