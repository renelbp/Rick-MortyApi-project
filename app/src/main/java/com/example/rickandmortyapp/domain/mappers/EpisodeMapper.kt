package com.example.rickandmortyapp.domain.mappers

import com.example.rickandmortyapp.model.Episode
import com.example.rickandmortyapp.model.network.GetEpisodeByIdResponse

object EpisodeMapper {
    fun buildFrom(response: GetEpisodeByIdResponse): Episode{
        return Episode(
            id = response.id,
            name = response.name,
            airDate = response.air_date,
            episode = response.episode
        )
    }
}