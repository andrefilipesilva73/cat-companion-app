package com.catcompanion.app

import com.catcompanion.app.api.CatApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CatApiServiceTest {
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

    @Test
    fun getBreeds() = runTest {
        // Get
        val breeds = catApiService.getBreeds(1, 0)
        assertEquals(1, breeds.size)
    }

    @Test
    fun getBreedsBySearch() = runTest {
        // Get
        val breeds = catApiService.getBreedsBySearch("American", 0)
        assertTrue(breeds.isNotEmpty())
    }

    @Test
    fun getBreedById() = runTest {
        // Get
        val breed = catApiService.getBreedById("ebur")
        assertNotNull(breed)
    }

    @Test
    fun getImageById() = runTest {
        // Get
        val image = catApiService.getImageById("njK25knLH")
        assertNotNull(image)
    }

    @Test
    fun getBreedImageBySearch() = runTest {
        // Get
        val images = catApiService.getBreedImageBySearch("ebur")
        assertTrue(images.isNotEmpty())
    }
}