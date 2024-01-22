package com.catcompanion.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.catcompanion.app.model.Breed
import com.catcompanion.app.repository.BreedRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseBreedViewModel(private val breedRepository: BreedRepository) : ViewModel() {
    // Breeds
    protected val _breedResponse: MutableStateFlow<PagingData<Breed>> =
        MutableStateFlow(PagingData.empty())
    val breeds = _breedResponse.asStateFlow()

    // Whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    // Search text typed by the user
    protected val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    // Search list
    protected val _searchResultsList = MutableStateFlow<List<Breed>>(emptyList<Breed>())
    val searchResultsList = _searchResultsList.asStateFlow()

    // Hold Search Job
    private var searchJob: Job? = null

    abstract fun fetchData()

    abstract fun performSearch()

    fun retry() {
        // Obtain Breeds data
        fetchData()
    }

    fun onQueryChange(text: String) {
        // Set search text
        _searchText.value = text

        // Cancel the previous search job (if any)
        searchJob?.cancel()

        // Start a new search job with a delay
        searchJob = viewModelScope.launch {
            delay(500L)
            performSearch()
        }
    }

    fun onSearch(text: String) {
        // Set search text
        _searchText.value = text

        // Close Search
        _isSearching.value = !_isSearching.value

        // Perform search
        performSearch()
    }

    fun onToggleSearch() {
        // Close Search
        _isSearching.value = !_isSearching.value

        // If a search value is defined
        if (!_isSearching.value) {
            // Change it to nothing
            onQueryChange("")
        }
    }

    fun addBreedToFavorites(breed: Breed) {
        // Find and update the breed in breeds
        _breedResponse.value = _breedResponse.value.map {
            if (it.id == breed.id) {
                it.copy(isFavorite = true)
            } else {
                it
            }
        }

        // Find and update the breed in searchResultsList
        _searchResultsList.value = _searchResultsList.value.map {
            if (it.id == breed.id) {
                it.copy(isFavorite = true)
            } else {
                it
            }
        }

        // Update
        viewModelScope.launch {
            breedRepository.addBreedToFavorites(breed);
        }
    }

    fun removeBreedFromFavorites(breed: Breed) {
        // Find and update the breed in breeds
        _breedResponse.value = _breedResponse.value.map {
            if (it.id == breed.id) {
                it.copy(isFavorite = false)
            } else {
                it
            }
        }

        // Find and update the breed in searchResultsList
        _searchResultsList.value = _searchResultsList.value.map {
            if (it.id == breed.id) {
                it.copy(isFavorite = false)
            } else {
                it
            }
        }

        // Update
        viewModelScope.launch {
            breedRepository.removeBreedFromFavorites(breed);
        }
    }
}