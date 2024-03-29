package com.catcompanion.app.view

// Import necessary components from the Jetpack Compose library
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.catcompanion.app.db.AppDatabase
import com.catcompanion.app.fragment.BreedsList
import com.catcompanion.app.fragment.BreedsListType
import com.catcompanion.app.repository.BreedRepository
import com.catcompanion.app.viewmodel.FavoriteViewModel

@Composable
fun FavoritesListScreen(navController: NavHostController) {
    // Context
    val context = LocalContext.current

    // Create View model
    val viewModel = remember {
        FavoriteViewModel(
            BreedRepository(
                AppDatabase.getInstance(context).breedDao()
            )
        )
    }

    // Build
    BreedsList(navController, viewModel, BreedsListType.Favorites, "home_favorites_screen")
}
