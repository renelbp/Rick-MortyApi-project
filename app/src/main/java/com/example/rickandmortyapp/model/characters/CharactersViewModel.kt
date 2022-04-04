package com.example.rickandmortyapp.model.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.rickandmortyapp.Constants
import com.example.rickandmortyapp.model.network.response.GetCharacterByIdResponse

class CharactersViewModel: ViewModel() {
    private val repository = CharacterRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(Constants.PAGE_SIZE)
        .setPrefetchDistance(Constants.PREFETCH_DISTANCE)
        .build()

    private val dataSourceFactory = CharactersDataSourceFactory(viewModelScope,repository)
    val charactersPagedListLiveData: LiveData<PagedList<GetCharacterByIdResponse>> = LivePagedListBuilder(
        dataSourceFactory,pageListConfig
    ).build()

}