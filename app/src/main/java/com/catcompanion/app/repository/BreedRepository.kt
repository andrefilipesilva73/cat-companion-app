package com.catcompanion.app.repository

import com.catcompanion.app.model.Breed

class BreedRepository {
    suspend fun getBreeds(): List<Breed> {
        // Simulate network call or fetch data from a remote server
        return listOf(
            Breed(1, "American Shorthair"),
            Breed(2, "Bengal"),
            // Add more breeds as needed
        )
    }
}