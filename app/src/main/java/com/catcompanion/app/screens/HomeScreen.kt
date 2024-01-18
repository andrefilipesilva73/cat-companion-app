package com.catcompanion.app.screens

// Import necessary components from the Jetpack Compose library
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// Annotate the function to opt into using ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(mainNavController: NavHostController, modifier: Modifier = Modifier) {
    // Create a NavHostController that handles the adding of the ComposeNavigator and DialogNavigator.
    val navController = rememberNavController()

    // Get the current back stack entry
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    // Get the current route from the back stack entry
    val currentRoute = currentBackStackEntry?.destination?.route

    // Content goes here
    Scaffold(
        topBar = {
            // TopAppBar goes here
            TopAppBar(
                title = { Text("Hello Scaffold!") }
            )
        },
        bottomBar = {
            // Bottom Navigation bar
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                // List item
                NavigationBarItem(
                    selected = currentRoute == "breedsListScreen",
                    onClick = {
                        navController.navigate("breedsListScreen") {
                            popUpTo(navController.graph.startDestinationRoute!!) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(Icons.Default.List, contentDescription = "List")
                    }
                )

                // Favorites item
                NavigationBarItem(
                    selected = currentRoute == "favoritesListScreen",
                    onClick = {
                        navController.navigate("favoritesListScreen") {
                            popUpTo(navController.graph.startDestinationRoute!!) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                    }
                )
            }
        }
    ) { innerPadding ->
        // Content goes here, create the NavHost
        NavHost(
            navController = navController,
            startDestination = "breedsListScreen",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Breeds List Screen
            composable("breedsListScreen") {
                BreedsListScreen(mainNavController)
            }

            // Favorites List Screen
            composable("favoritesListScreen") {
                FavoritesListScreen(mainNavController)
            }
        }
    }
}
