package com.catcompanion.app.view

// Import necessary components from the Jetpack Compose library
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

// Declare a composable function that represents the UI for the BreedDetailScreen
@Composable
fun BreedDetailScreen(navController: NavHostController, breed: String) {
    // Example of navigating back to the previous screen
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
    }
}
