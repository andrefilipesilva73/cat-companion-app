package com.catcompanion.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.catcompanion.app.model.Breed
import com.catcompanion.app.model.User

@Dao
interface BreedDao {
    @Insert
    suspend fun insert(breed: Breed)

    @Update
    suspend fun update(breed: Breed)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(breed: Breed)

    @Query("SELECT * FROM breeds")
    suspend fun getAll(): List<Breed>
}
