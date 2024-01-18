// Declare the package where this class belongs
package com.catcompanion.app

// Import necessary Android and Jetpack Compose libraries
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.catcompanion.app.screens.BreedsListScreen
import com.catcompanion.app.screens.FavoritesListScreen
import com.catcompanion.app.screens.BreedDetailScreen
import com.catcompanion.app.ui.theme.CatCompanionAppTheme

// Declare the MainActivity class, extending ComponentActivity
class MainActivity : ComponentActivity() {
    // Override the onCreate method to set up the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content of the activity using Jetpack Compose
        setContent {
            // Apply the CatCompanionAppTheme to the entire content
            CatCompanionAppTheme {
                // Create a NavHostController that handles the adding of the ComposeNavigator and DialogNavigator.
                val navController = rememberNavController()

                // Create the NavHost
                NavHost(
                    navController = navController,
                    startDestination = "breedsListScreen"
                ) {
                    // Breeds List Screen
                    composable("breedsListScreen") {
                        BreedsListScreen(navController)
                    }

                    // Favorites List Screen
                    composable("favoritesListScreen") {
                        FavoritesListScreen(navController)
                    }

                    // Breed Detail Screen
                    composable("breedDetailScreen/{breed}") { backStackEntry ->
                        // Instantiate BreedDetailScreen with parameters, using data from the back stack entry
                        // backStackEntry is an object representing the current state in the navigation back stack
                        BreedDetailScreen(
                            navController, // Pass the navigation controller to the BreedDetailScreen
                            breed = backStackEntry.arguments?.getString("breed") ?: "" // Retrieve the "breed" argument from the back stack entry; use an empty string if null
                        )
                    }
                }
            }
        }
    }
}

// Declare a composable function Greeting, taking a name and an optional modifier
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // Display a Text composable with a greeting message
    Text(
        text = "Hello $name!", // Concatenate the greeting with the provided name
        modifier = modifier // Apply the optional modifier
    )
}

// Declare a composable function GreetingPreview for previewing the Greeting composable
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    // Apply the CatCompanionAppTheme to the preview
    CatCompanionAppTheme {
        // Call the Greeting composable function with the parameter "Android" for preview
        Greeting("Android")
    }
}