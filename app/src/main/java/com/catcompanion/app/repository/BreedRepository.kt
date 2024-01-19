package com.catcompanion.app.repository

import com.catcompanion.app.model.Breed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class BreedRepository {
    private val defaultBreeds = mutableListOf(
        Breed(1, "American Shorthair"),
        Breed(2, "Bengal"),
        // Add more breeds as needed
    )

    private val breeds: MutableList<Breed> = mutableListOf()

    suspend fun getBreeds(): Flow<List<Breed>> {
        // Simulate fetching users from a data source (e.g., database)
        delay(5000)

        // Use MutableStateFlow for simplicity, replace with appropriate Flow implementation
        return MutableStateFlow(defaultBreeds.toList())
    }
}