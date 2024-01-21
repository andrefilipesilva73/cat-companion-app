package com.catcompanion.app.model

data class Breed (
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val imageUrl: String,
    val isFavorite: Boolean
)