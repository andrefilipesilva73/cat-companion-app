package com.catcompanion.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.catcompanion.app.model.Breed
import com.catcompanion.app.repository.BreedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    // Search list
    private val _searchResultsList = MutableStateFlow<List<Breed>>(emptyList<Breed>())
    val searchResultsList = searchText
        .combine(_searchResultsList) { text, _ ->// Combine searchText with _searchResultsList
            // Evaluate current text
            if (text.isBlank()) { // Return an empty list (it could be improved with recent breeds or favorites, etc).
                emptyList<Breed>()
            } else {
                try {
                    // Otherwise, filter and return a list of names based on the text the user typed
                    breedRepository.getBreedsBySearch(text)
                } catch (e: Exception) {
                    // Handle errors (e.g., network issues)
                    e.printStackTrace()

                    // Return an empty list (This should be improved later)
                    emptyList<Breed>()
                }
            }
        }.stateIn( // Basically convert the Flow returned from combine operator to StateFlow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // It will allow the StateFlow survive 5 seconds before it been canceled
            initialValue = _searchResultsList.value
        )

    abstract fun fetchData()

    fun retry() {
        // Obtain Breeds data
        fetchData()
    }

    fun onQueryChange(text: String) {
        // Set search text
        _searchText.value = text
    }

    fun onSearch(text: String) {
        // Set search text
        _searchText.value = text

        // Close Search
        _isSearching.value = !_isSearching.value
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