package com.catcompanion.app.repository

import com.catcompanion.app.BuildConfig
import com.catcompanion.app.api.CatApiService
import com.catcompanion.app.model.Breed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
        Breed("1", "American Shorthair"),
        Breed("2", "Bengal"),
        // Add more breeds as needed
    )

    private val breeds: MutableList<Breed> = mutableListOf()

    suspend fun getBreeds(): Flow<List<Breed>> {
        try {
            // Fetch breeds from the API
            val apiBreeds = catApiService.getBreeds(2, 0)

            // Update the breeds list
            breeds.clear()
            breeds.addAll(apiBreeds)

            // Use MutableStateFlow for simplicity, replace with appropriate Flow implementation
            return MutableStateFlow(breeds.toList())
        } catch (e: Exception) {
            // Handle errors (e.g., network issues)
            e.printStackTrace()

            // Return the default breeds in case of an error
            return MutableStateFlow(defaultBreeds.toList())
        }
    }
}