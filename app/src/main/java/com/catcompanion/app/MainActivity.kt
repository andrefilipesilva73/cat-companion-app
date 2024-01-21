// Declare the package where this class belongs
package com.catcompanion.app

// Import necessary Android and Jetpack Compose libraries
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.catcompanion.app.view.BreedDetailScreen
import com.catcompanion.app.view.Home
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
                Main()
            }
        }
    }
}

// Declare a composable function Main, taking an optional modifier
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(modifier: Modifier = Modifier) {
    // Create a NavHostController that handles the adding of the ComposeNavigator and DialogNavigator.
    val navController = rememberNavController()

    // Content goes here, create the NavHost
    NavHost(
        navController = navController,
        startDestination = "home",
    ) {
        // Home Screen
        composable("home") {
            Home(navController)
        }

        // Breed Detail Screen
        composable("breedDetailScreen/{breedId}") { backStackEntry ->
            // Instantiate BreedDetailScreen with parameters, using data from the back stack entry
            // backStackEntry is an object representing the current state in the navigation back stack
            BreedDetailScreen(
                navController, // Pass the navigation controller to the BreedDetailScreen
                breedId = backStackEntry.arguments?.getString("breedId") ?: "" // Retrieve the "breed" argument from the back stack entry; use an empty string if null
            )
        }
    }
}

// Declare a composable function MainPreview for previewing the Main composable
@Preview(showBackground = true)
@Composable
fun MainPreview() {
    // Apply the CatCompanionAppTheme to the preview
    CatCompanionAppTheme {
        // Call the Main composable function for preview
        Main()
    }
}