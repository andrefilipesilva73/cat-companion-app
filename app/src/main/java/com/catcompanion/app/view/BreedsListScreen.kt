@file:OptIn(ExperimentalMaterial3Api::class)

package com.catcompanion.app.view

// Import necessary components from the Jetpack Compose library
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BreedsListScreenContent(navController: NavHostController) {
    val range = 1..100

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(
            ),
        // contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(range.count()) { index ->
            Card(
                modifier = Modifier.padding(horizontal = 16.dp),
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
                Text(text = "- List item number ${index + 1}")
            }
        }
    }
}

// Declare a composable function that represents the UI for the BreedsListScreen
@Composable
fun BreedsListScreen(navController: NavHostController) {
    // Example of navigating to the detail screen when a card is clicked
    BreedsListScreenContent(navController)
}
