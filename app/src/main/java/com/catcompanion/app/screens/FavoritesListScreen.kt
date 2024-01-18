package com.catcompanion.app.screens

// Import necessary components from the Jetpack Compose library
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

// Annotate the function to opt into using ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
// Declare a composable function that represents the UI for the com.catcompanion.app.screens.FavoritesListScreen
@Composable
fun FavoritesListScreen(navController: NavHostController) {
    // Example of navigating to the detail screen when a card is clicked
    Card(
        onClick = {
            // Use the navigation controller to navigate to the "breedDetailScreen/{breed}" destination
            navController.navigate("breedDetailScreen/{breed}") {
                // Configure the navigation behavior
                launchSingleTop = true // Launch as a single top-level destination
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true // Save the state of the popped destinations
                }
            }
        }
    ) {
        // Inside the Card composable, define the content of the card
        Text(text = "Go to Breed Detail from Favorites") // Display a text inside the card
    }
}
