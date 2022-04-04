package com.example.rickandmortyapp.model.network

import com.example.rickandmortyapp.model.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.model.network.response.GetCharactersPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character/{characterId}")
    suspend fun getCharacterById(@Path("characterId") characterId: Int) : Response<GetCharacterByIdResponse>

    @GET("character")
    suspend fun getCharactersPage(@Query("page") pageIndex: Int): Response<GetCharactersPageResponse>

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(
        @Path("episode-id")episodeId: Int): Response<GetEpisodeByIdResponse>

    @GET("episode/{episode-range}")
    suspend fun getEpisodeRange(
        @Path("episode-range")episodeRange: String): Response<List<GetEpisodeByIdResponse>>

}