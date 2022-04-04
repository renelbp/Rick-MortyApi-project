package com.example.rickandmortyapp.model.characters

import com.example.rickandmortyapp.model.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope

class CharactersDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository):
    androidx.paging.DataSource.Factory<Int, GetCharacterByIdResponse >() {

    override fun create(): androidx.paging.DataSource<Int, GetCharacterByIdResponse> {
        return CharactersDataSource(coroutineScope,repository)
    }
}