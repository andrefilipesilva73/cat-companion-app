package com.catcompanion.app.api

import com.catcompanion.app.model.Breed
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class BreedCatApi (
    val id: String,
    val name: String,
    val temperament: String,
    var reference_image_id: String?
)

data class ImageCatApi (
    val id: String,
    val url: String
)

interface CatApiService {

    @GET("v1/breeds")
    suspend fun getBreeds(@Query("limit") limit: Int, @Query("page") page: Int): List<BreedCatApi>

    @GET("v1/breeds/search")
    suspend fun getBreedsBySearch(@Query("q") breedName: String, @Query("attach_image") attachImage: Int): List<BreedCatApi>

    @GET("v1/breeds/{breedId}")
    suspend fun getBreedById(@Path("breedId") breedId: String): BreedCatApi

    @GET("v1/images/{imageId}")
    suspend fun getImageById(@Path("imageId") imageId: String): ImageCatApi

    @GET("v1/images/search")
    suspend fun getBreedImageBySearch(@Query("breed_ids") breedId: String): List<ImageCatApi>

}
