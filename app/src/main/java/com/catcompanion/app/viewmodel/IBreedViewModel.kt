package com.catcompanion.app.viewmodel

import androidx.paging.PagingData
import com.catcompanion.app.model.Breed
import kotlinx.coroutines.flow.StateFlow

interface IBreedViewModel {
    val breeds: StateFlow<PagingData<Breed>>
    val isSearching: StateFlow<Boolean>
    val searchText: StateFlow<String>
    val searchResultsList: StateFlow<List<Breed>>

    fun retry()
    fun onQueryChange(text: String)
    fun onSearch(text: String)
    fun onToggleSearch()
    fun addBreedToFavorites(breedId: String)
    fun removeBreedFromFavorites(breedId: String)
}