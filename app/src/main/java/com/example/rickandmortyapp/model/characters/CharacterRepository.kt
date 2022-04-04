package com.example.rickandmortyapp.model.characters

import com.example.rickandmortyapp.model.network.NetworkLayer
import com.example.rickandmortyapp.model.network.response.GetCharactersPageResponse

class CharacterRepository {
   suspend fun getCharactersPage(pageIndex: Int): GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(pageIndex)
        if(request.failed || !request.isSuccesful){
            return null
        }
        return request.body
    }

}
