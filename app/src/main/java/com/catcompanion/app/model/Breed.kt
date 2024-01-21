package com.catcompanion.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class Breed (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val imageUrl: String,
    val isFavorite: Boolean
)