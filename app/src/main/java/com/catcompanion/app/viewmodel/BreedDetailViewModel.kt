package com.catcompanion.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catcompanion.app.model.Breed
import com.catcompanion.app.repository.BreedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedDetailViewModel(private val breedRepository: BreedRepository) : ViewModel() {
    // Selected Breed
    private val _selectedBreed: MutableStateFlow<Breed?> = MutableStateFlow(null)
    val selectedBreed: StateFlow<Breed?> = _selectedBreed.asStateFlow()

    // Is loading Selected Breed
    private val _isLoadingSelectedBreed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoadingSelectedBreed: StateFlow<Boolean> = _isLoadingSelectedBreed.asStateFlow()

    init {}

    // Fetch a Breed by Id
    fun fetchBreedById(breedId: String) {
        viewModelScope.launch {
            try {
                _isLoadingSelectedBreed.value = true // Set loading state to true

                val selectedBreedById = breedRepository.getBreedById(breedId)
                _selectedBreed.value = selectedBreedById
            } catch (exception: Exception) {
                // Handle errors (e.g., network issues)
                exception.printStackTrace()

                // Handle the error appropriately (show a message, etc.)
                _selectedBreed.value = null
            } finally {
                _isLoadingSelectedBreed.value = false // Set loading state to false after the operation is complete
            }
        }
    }

    fun addBreedToFavorites(breed: Breed) {
        // Update
        selectedBreed.value?.isFavorite = true

        // Update
        viewModelScope.launch {
            breedRepository.addBreedToFavorites(breed);
        }
    }

    fun removeBreedFromFavorites(breed: Breed) {
        // Update
        selectedBreed.value?.isFavorite = false

        // Update
        viewModelScope.launch {
            breedRepository.removeBreedFromFavorites(breed);
        }
    }
}
