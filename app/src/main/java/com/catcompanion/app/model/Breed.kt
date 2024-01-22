package com.catcompanion.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.catcompanion.app.db.DatabaseConverters

@Entity(tableName = "breeds")
@TypeConverters(DatabaseConverters::class)
data class Breed (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var name: String,
    var temperament: String,
    var origin: String,
    var description: String,
    var lifeSpan: String,
    var imageUrl: String,
    var isFavorite: Boolean
)