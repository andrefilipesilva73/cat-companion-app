package com.catcompanion.app.repository

import android.util.Log
import com.catcompanion.app.BuildConfig
import com.catcompanion.app.api.CatApiService
import com.catcompanion.app.model.Breed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BreedRepository {
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

    suspend fun getBreedsByPages(limit: Int, page: Int): List<Breed> {
        return try {
            // Fetch breeds from the API
            val response = catApiService.getBreeds(limit, page)

            // Use coroutineScope to perform parallel tasks
            coroutineScope {
                // Use async to fetch image URLs in parallel
                val imageUrlsDeferred = response.map { apiBreed ->
                    async {
                        getImageUrl(apiBreed.reference_image_id, apiBreed.id) }
                }

                // Map the response to your Breed model with image URLs
                val mappedBreeds = response.mapIndexed { index, apiBreed ->
                    Breed(
                        apiBreed.id,
                        apiBreed.name,
                        apiBreed.temperament,
                        imageUrlsDeferred[index].await()
                    )
                }

                // Return the mapped breeds
                mappedBreeds
            }
        } catch (e: Exception) {
            // Handle errors (e.g., network issues)
            e.printStackTrace()

            // Return the default breeds in case of an error
            throw e
        }
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

                // Return a default value or rethrow the exception
                return@withContext ""
            }
        }
    }

    suspend fun getBreedsBySearch(breedName: String): List<Breed> {
        return try {
            // Fetch breeds from the API
            val response = catApiService.getBreedsBySearch(breedName, 1)

            // Use coroutineScope to perform parallel tasks
            coroutineScope {
                // Use async to fetch image URLs in parallel
                val imageUrlsDeferred = response.map { apiBreed ->
                    async {
                        getImageUrl(apiBreed.reference_image_id, apiBreed.id) }
                }

                // Map the response to your Breed model with image URLs
                val mappedBreeds = response.mapIndexed { index, apiBreed ->
                    Breed(
                        apiBreed.id,
                        apiBreed.name,
                        apiBreed.temperament,
                        imageUrlsDeferred[index].await()
                    )
                }

                // Return the mapped breeds
                mappedBreeds
            }
        } catch (e: Exception) {
            // Handle errors (e.g., network issues)
            e.printStackTrace()

            // Return the default breeds in case of an error
            throw e
        }
    }
}