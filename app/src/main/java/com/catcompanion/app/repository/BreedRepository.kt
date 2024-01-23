package com.catcompanion.app.repository

import com.catcompanion.app.BuildConfig
import com.catcompanion.app.api.BreedCatApi
import com.catcompanion.app.api.CatApiService
import com.catcompanion.app.db.BreedDao
import com.catcompanion.app.model.Breed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BreedRepository (private val breedDao: BreedDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    // Cat API Service
    private val catApiService: CatApiService by lazy {
        // Define the interceptor, add authentication headers
        val interceptor = Interceptor { chain ->
            val newRequest: Request =
                chain.request().newBuilder().addHeader("x-api-key", BuildConfig.CAT_API_API_KEY).build()
            chain.proceed(newRequest)
        }

        // Add the interceptor to OkHttpClient
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptor)
        val client = builder.build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.CAT_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CatApiService::class.java)
    }

    private suspend fun getImageUrl(imageId: String?, breedId: String): String {
        return withContext(Dispatchers.IO) {
            try {
                // Evaluate image existence
                if (imageId == null) {
                    // Perform the secondary HTTP request to fetch image URL
                    val imagesUrlResponse = catApiService.getBreedImageBySearch(breedId)
                    return@withContext imagesUrlResponse[0].url
                } else {
                    // Perform the secondary HTTP request to fetch image URL
                    val imageUrlResponse = catApiService.getImageById(imageId)
                    return@withContext imageUrlResponse.url
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle the exception as needed
                // For debugging, print the exception message
                println("Exception while fetching image URL: ${e.message}")

                // Look for a previous link on the database
                val breed = breedDao.getById(breedId)

                // Evaluate
                if (breed != null && breed.imageUrl != "") {
                    return@withContext breed.imageUrl
                } else {
                    // Return a default value or rethrow the exception
                    return@withContext ""
                }
            }
        }
    }

    private suspend fun mapApiResponse(breeds: List<BreedCatApi>): List<Breed> {
        return coroutineScope {
            // Use async to fetch image URLs in parallel
            val imageUrlsDeferred = breeds.map { apiBreed ->
                async {
                    getImageUrl(apiBreed.reference_image_id, apiBreed.id)
                }
            }

            // Use async to fetch favorite status from Room database
            val favoritesDeferred = breeds.map { apiBreed ->
                async {
                    breedDao.isFavorite(apiBreed.id) // Assuming you have a function in BreedDao to check if a breed is favorite
                }
            }

            // Map the response to your Breed model with image URLs
            val mappedBreeds = breeds.mapIndexed { index, apiBreed ->
                Breed(
                    apiBreed.id,
                    apiBreed.name,
                    apiBreed.temperament,
                    apiBreed.origin,
                    apiBreed.description,
                    apiBreed.life_span,
                    imageUrlsDeferred[index].await(),
                    favoritesDeferred[index].await()
                )
            }

            // Return the mapped breeds
            mappedBreeds
        }
    }

    suspend fun getBreedsByPages(limit: Int, page: Int): List<Breed> {
        return try {
            // Fetch breeds from the API
            val response = catApiService.getBreeds(limit, page)

            // Use the fetchImageUrlsParallel function
            mapApiResponse(response)
        } catch (e: Exception) {
            // Handle errors (e.g., network issues)
            e.printStackTrace()

            // Let's give it another change
            withContext(Dispatchers.IO) {
                // Fetch breeds from the Database
                return@withContext breedDao.getPagedBreeds(limit, page * limit)
            }
        }
    }

    suspend fun getBreedsBySearch(breedName: String): List<Breed> {
        return try {
            // Fetch breeds from the API
            val response = catApiService.getBreedsBySearch(breedName, 1)

            // Use the fetchImageUrlsParallel function
            mapApiResponse(response)
        } catch (e: Exception) {
            // Handle errors (e.g., network issues)
            e.printStackTrace()

            // Let's give it another change
            withContext(Dispatchers.IO) {
                // Fetch breeds from the Database
                return@withContext breedDao.searchBreedsByName(breedName)
            }
        }
    }

    suspend fun getBreedById(breedId: String): Breed? {
        return try {
            // Fetch breed details from the API
            val response = listOf(catApiService.getBreedById(breedId))

            // Use the fetchImageUrlsParallel function
            val breeds = mapApiResponse(response)

            // Return the first breed, or null if the list is empty
            breeds.firstOrNull()
        } catch (e: Exception) {
            // Handle errors (e.g., network issues)
            e.printStackTrace()

            // Let's give it another change
            withContext(Dispatchers.IO) {
                // Fetch breeds from the Database
                return@withContext breedDao.getById(breedId)
            }
        }
    }

    suspend fun addBreedToFavorites(breed: Breed) {
        coroutineScope.launch(Dispatchers.IO) {
            // Save on Local Database
            breed.isFavorite = true

            try {
                breedDao.insertOrUpdate(breed)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun removeBreedFromFavorites(breed: Breed) {
        coroutineScope.launch(Dispatchers.IO) {
            // Save on Local Database
            breed.isFavorite = false

            try {
                breedDao.insertOrUpdate(breed)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getFavoritesByPages(limit: Int, page: Int): List<Breed> {
        try {
            return withContext(Dispatchers.IO) {
                // Fetch breeds from the API
                try {
                    val response = breedDao.getPagedFavoriteBreeds(limit, page * limit)

                    // Ready
                    response
                } catch (e: Exception) {
                    e.printStackTrace()

                    // Return nothing
                    emptyList()
                }
            }
        } catch (e: Exception) {
            // Handle errors (e.g., network issues)
            e.printStackTrace()

            // Return the default breeds in case of an error
            throw e
        }
    }

    suspend fun getFavoritesBySearch(breedName: String): List<Breed> {
        try {
            return withContext(Dispatchers.IO) {
                // Fetch breeds from the API
                try {
                    val response = breedDao.searchFavoritesByName(breedName)

                    // Ready
                    response
                } catch (e: Exception) {
                    e.printStackTrace()

                    // Return nothing
                    emptyList()
                }
            }
        } catch (e: Exception) {
            // Handle errors (e.g., network issues)
            e.printStackTrace()

            // Return the default breeds in case of an error
            throw e
        }
    }

    suspend fun updateDatabase(breeds: List<Breed>) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                breedDao.insertOrUpdateMany(breeds)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun readDatabase(): List<Breed> {
        try {
            return withContext(Dispatchers.IO) {
                // Fetch breeds from the API
                try {
                    val response = breedDao.getAll()

                    // Ready
                    response
                } catch (e: Exception) {
                    e.printStackTrace()

                    // Return nothing
                    emptyList()
                }
            }
        } catch (e: Exception) {
            // Handle errors (e.g., network issues)
            e.printStackTrace()

            // Return the default breeds in case of an error
            throw e
        }
    }

}