package com.catcompanion.app.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catcompanion.app.model.Breed
import com.catcompanion.app.repository.BreedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BreedViewModel(private val breedRepository: BreedRepository) : ViewModel() {
    private val _breeds: MutableStateFlow<List<Breed>> = MutableStateFlow(emptyList())
    val breeds: StateFlow<List<Breed>> get() = _breeds

    init {
        // Fetch initial users data
        fetchBreeds()
    }

    private fun fetchBreeds() {
        viewModelScope.launch {
            _breeds.value = breedRepository.getBreeds().first()
        }
    }
}
