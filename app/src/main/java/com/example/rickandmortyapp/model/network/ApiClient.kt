package com.example.rickandmortyapp.model.network

import com.example.rickandmortyapp.model.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.model.network.response.GetCharactersPageResponse
import retrofit2.Response
import java.lang.Exception

class ApiClient(private val rickAndMortService: RickAndMortyService) {

    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall{
            rickAndMortService.getCharacterById(characterId)
        }
    }

    suspend fun getCharactersPage(pageIndex: Int): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall {rickAndMortService.getCharactersPage(pageIndex)  }
    }

    suspend fun getEpisodeById(episodeId: Int): SimpleResponse<GetEpisodeByIdResponse>{
        return safeApiCall { rickAndMortService.getEpisodeById(episodeId) }
    }

    suspend fun getEpisodeRange(episodeRange: String): SimpleResponse<List<GetEpisodeByIdResponse>>{
        return safeApiCall { rickAndMortService.getEpisodeRange(episodeRange) }
    }

    private inline fun <T> safeApiCall(apiCall:() -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.succes(apiCall.invoke())
        }catch (e: Exception){
            SimpleResponse.failure(e)
        }
    }

}

