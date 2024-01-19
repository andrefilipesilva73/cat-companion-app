package com.catcompanion.app.api

import com.catcompanion.app.model.Breed
import retrofit2.http.GET
import retrofit2.http.Query

data class BreedCatApi (
    val id: String,
    val name: String,
    var reference_image_id: String
)

interface CatApiService {

    @GET("v1/breeds")
    suspend fun getBreeds(@Query("limit") limit: Int, @Query("page") page: Int): List<BreedCatApi>
}
