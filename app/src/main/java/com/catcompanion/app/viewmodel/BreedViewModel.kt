package com.catcompanion.app.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.catcompanion.app.model.Breed
import com.catcompanion.app.repository.BreedRepository
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

class BreedViewModel(private val breedRepository: BreedRepository) : BaseBreedViewModel(breedRepository) {
    init {
        fetchData()
    }

    override fun fetchData() {
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

    override fun performSearch() {
        viewModelScope.launch {
            try {
                // Perform the search using the repository
                val searchResults = breedRepository.getBreedsBySearch(_searchText.value)

                // Update the search results list
                _searchResultsList.value = searchResults
            } catch (e: Exception) {
                // Handle errors (e.g., network issues)
                e.printStackTrace()

                // In case of an error, update the search results list with an empty list
                _searchResultsList.value = emptyList()
            }
        }
    }
}
