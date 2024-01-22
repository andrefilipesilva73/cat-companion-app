package com.catcompanion.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import androidx.paging.map
import com.catcompanion.app.model.Breed
import com.catcompanion.app.repository.BreedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BreedPagingSource(
    private val breedRepository: BreedRepository,
) : PagingSource<Int, Breed>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Breed> {
        return try {
            val currentPage = params.key ?: 1
            val response = breedRepository.getBreedsByPages(10, currentPage - 1)
            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Breed>): Int? {
        return state.anchorPosition
    }

}

class BreedViewModel(private val breedRepository: BreedRepository) : ViewModel(), IBreedViewModel {
    // Breeds
    private val _breedResponse: MutableStateFlow<PagingData<Breed>> =
        MutableStateFlow(PagingData.empty())
    override val breeds = _breedResponse.asStateFlow()

    // Whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    override val isSearching = _isSearching.asStateFlow()

    // Search text typed by the user
    private val _searchText = MutableStateFlow("")
    override val searchText = _searchText.asStateFlow()

    // Search list
    private val _searchResultsList = MutableStateFlow<List<Breed>>(emptyList<Breed>())
    override val searchResultsList = searchText
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

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    10, enablePlaceholders = true
                )
            ) {
                BreedPagingSource(breedRepository)
            }.flow.cachedIn(viewModelScope).collect {
                _breedResponse.value = it
            }
        }
    }

    override fun retry() {
        // Obtain Breeds data
        fetchData()
    }

    override fun onQueryChange(text: String) {
        // Set search text
        _searchText.value = text
    }

    override fun onSearch(text: String) {
        // Set search text
        _searchText.value = text

        // Close Search
        _isSearching.value = !_isSearching.value
    }

    override fun onToggleSearch() {
        // Close Search
        _isSearching.value = !_isSearching.value

        // If a search value is defined
        if (!_isSearching.value) {
            // Change it to nothing
            onQueryChange("")
        }
    }

    override fun addBreedToFavorites(breed: Breed) {
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

    override fun removeBreedFromFavorites(breed: Breed) {
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
