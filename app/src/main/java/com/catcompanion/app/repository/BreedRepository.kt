package com.catcompanion.app.repository

import com.catcompanion.app.BuildConfig
import com.catcompanion.app.api.CatApiService
import com.catcompanion.app.model.Breed
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

    private val defaultBreeds = mutableListOf(
        Breed("1", "American Shorthair", "1"),
        Breed("2", "Bengal", "2"),
        // Add more breeds as needed
    )

    suspend fun getBreedsByPages(limit: Int, page: Int): List<Breed> {
        return try {
            // Fetch breeds from the API
            val response = catApiService.getBreeds(limit, page)

            // Map the response to your Breed model
            val mappedBreeds = response.map { apiBreed ->
                Breed(
                    apiBreed.id,
                    apiBreed.name,
                    "${BuildConfig.CAT_API_BASE_URL}/v1/images/${apiBreed.reference_image_id}"
                )
            }

            // Return the mapped breeds
            mappedBreeds
        } catch (e: Exception) {
            // Handle errors (e.g., network issues)
            e.printStackTrace()

            // Return the default breeds in case of an error
            defaultBreeds.toList()
        }
    }
}