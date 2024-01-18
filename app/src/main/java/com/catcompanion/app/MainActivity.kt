// Declare the package where this class belongs
package com.catcompanion.app

// Import necessary Android and Jetpack Compose libraries
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), // Set the size to fill the maximum available space
                    color = MaterialTheme.colorScheme.background // Set the background color from the MaterialTheme
                ) {
                    // Call the Greeting composable function with the parameter "Android"
                    Greeting("Android")
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