package com.example.rickandmortyapp.model.characters

import androidx.paging.PageKeyedDataSource
import com.example.rickandmortyapp.model.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharactersDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository
):PageKeyedDataSource<Int, GetCharacterByIdResponse>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetCharacterByIdResponse>,
    ) {
        coroutineScope.launch {
            val page  = repository.getCharactersPage(1)
            if (page == null) {
                callback.onResult(emptyList(), null, null)
                return@launch
            }
            callback.onResult(page.results,null,getPageIndexFroMnEXT(page.info.next))
        }
    }
    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>,
    ) {
        coroutineScope.launch {
            val page = repository.getCharactersPage(params.key)
            if (page == null) {
                callback.onResult(emptyList(), null)
                return@launch
            }
            callback.onResult(page.results,getPageIndexFroMnEXT(page.info.next))
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>,
    ) {
        TODO("Not yet implemented")
    }

    private fun getPageIndexFroMnEXT(next: String?): Int?{
        return next?.split("?page=")?.get(1)?.toInt()
    }
}