package com.catcompanion.app.view

// Import necessary components from the Jetpack Compose library
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.catcompanion.app.fragment.BreedsList
import com.catcompanion.app.repository.BreedRepository
import com.catcompanion.app.viewmodel.BreedViewModel

@Composable
fun BreedsListScreen(navController: NavHostController) {
    // Create View model
    val viewModel = remember { BreedViewModel(BreedRepository()) }

    // Build
    BreedsList(navController, viewModel)
}
