package com.catcompanion.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.catcompanion.app.model.Breed

@Dao
interface BreedDao {
    @Insert
    suspend fun insert(breed: Breed)

    @Update
    suspend fun update(breed: Breed)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(breed: Breed)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateMany(breeds: List<Breed>)

    @Query("SELECT * FROM breeds")
    suspend fun getAll(): List<Breed>

    @Query("SELECT * FROM breeds WHERE id = :breedId")
    suspend fun getById(breedId: String): Breed?

    @Query("SELECT * FROM breeds ORDER BY name ASC LIMIT :limit OFFSET :offset")
    fun getPagedBreeds(limit: Int, offset: Int): List<Breed>

    @Query("SELECT * FROM breeds WHERE name LIKE '%' || :name || '%'")
    fun searchBreedsByName(name: String): List<Breed>

    @Query("SELECT * FROM breeds WHERE isFavorite = 1 ORDER BY name ASC LIMIT :limit OFFSET :offset")
    fun getPagedFavoriteBreeds(limit: Int, offset: Int): List<Breed>

    @Query("SELECT isFavorite FROM breeds WHERE id = :breedId")
    suspend fun isFavorite(breedId: String): Boolean

    @Query("SELECT * FROM breeds WHERE isFavorite = 1 AND name LIKE '%' || :name || '%'")
    fun searchFavoritesByName(name: String): List<Breed>
}
