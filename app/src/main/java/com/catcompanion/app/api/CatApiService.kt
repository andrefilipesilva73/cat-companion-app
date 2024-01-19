package com.catcompanion.app.api

import com.catcompanion.app.model.Breed
import retrofit2.http.GET

interface CatApiService {

    @GET("v1/breeds")
    suspend fun getBreeds(): List<Breed>
}
