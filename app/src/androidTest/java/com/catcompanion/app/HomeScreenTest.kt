package com.catcompanion.app

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.catcompanion.app.db.AppDatabase
import com.catcompanion.app.fragment.BreedsList
import com.catcompanion.app.fragment.BreedsListType
import com.catcompanion.app.repository.BreedRepository
import com.catcompanion.app.view.BreedsListScreen
import com.catcompanion.app.view.Home
import com.catcompanion.app.viewmodel.BreedViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Main Activity Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomNavigationBarNavigation() {
        // Start the app
        composeTestRule.setContent {
            // Create a NavHostController that handles the adding of the ComposeNavigator and DialogNavigator.
            val navController = rememberNavController()

            // Call the entry point composable function
            Home(navController)
        }

        // Make sure that "bottom_navigation_bar" is loaded
        composeTestRule.onNodeWithTag("bottom_navigation_bar").assertExists()

        // Click on the Favorites button
        composeTestRule.onNodeWithTag("bottom_navigation_bar_favorites").performClick()

        // After the click, assert that the corresponding screen is loaded
        composeTestRule.onNodeWithTag("home_favorites_screen").assertExists()

        // Click on the Breeds Button
        composeTestRule.onNodeWithTag("bottom_navigation_bar_breeds").performClick()

        // After the click, assert that the corresponding screen is loaded
        composeTestRule.onNodeWithTag("home_breeds_screen").assertExists()
    }

    @Test
    fun breedListLoading() {
        // Start the app
        composeTestRule.setContent {
            // Create a NavHostController that handles the adding of the ComposeNavigator and DialogNavigator.
            val navController = rememberNavController()

            // Call the entry point composable function
            BreedsListScreen(navController)
        }

        // Wait for loadings to end
        Thread.sleep(5000)

        // Look for any result in list
        composeTestRule.onAllNodesWithTag("breed_card")[0].assertExists()
    }

    @Test
    fun markAsFavoriteAndListLoading() {
        // Start the app
        composeTestRule.setContent {
            // Create a NavHostController that handles the adding of the ComposeNavigator and DialogNavigator.
            val navController = rememberNavController()

            // Call the entry point composable function
            Home(navController)
        }

        // Wait for loadings to end
        Thread.sleep(5000)

        // Turn card on a Favorite
        composeTestRule.onAllNodesWithTag("favorite_button")[0].performClick()

        // Click on the Favorites button
        composeTestRule.onNodeWithTag("bottom_navigation_bar_favorites").performClick()

        // Wait for loadings to end
        Thread.sleep(1000)

        // Look for any result in list
        composeTestRule.onAllNodesWithTag("favorite_card")[0].assertExists()

        // Remove from favorites
        composeTestRule.onAllNodesWithTag("favorite_button")[0].performClick()
    }

    @Test
    fun breedCardNavigationToDetail() {
        // Start the app
        composeTestRule.setContent {
            // Call the entry point composable function
            Main()
        }

        // Wait for loadings to end
        Thread.sleep(5000)

        // Look for any result in list
        composeTestRule.onAllNodesWithTag("breed_card")[0].assertExists()

        // Click card on a Card
        composeTestRule.onAllNodesWithTag("breed_card")[0].performClick()

        // After the click, assert that the corresponding screen is loaded
        composeTestRule.onNodeWithTag("breed_detail_screen").assertExists()
    }

    @Test
    fun searchBar() {
        // Create View model
        var viewModel: BreedViewModel? = null

        // Start the app
        composeTestRule.setContent {
            // Create a NavHostController that handles the adding of the ComposeNavigator and DialogNavigator.
            val navController = rememberNavController()

            // Context
            val context = LocalContext.current

            // Create View model
            viewModel = remember {
                BreedViewModel(
                    BreedRepository(
                        AppDatabase.getInstance(context).breedDao()
                    )
                )
            }

            // Call the entry point composable function
            BreedsList(navController, viewModel!!, BreedsListType.Breeds, "home_breeds_screen")
        }

        // Look for the search bar
        composeTestRule.onNodeWithTag("home_breeds_screen_search_bar").assertExists()

        // Click card on the search bar
        composeTestRule.onNodeWithTag("home_breeds_screen_search_bar").performClick()

        // Change query
        viewModel?.onQueryChange("American")

        // Wait for loadings to end
        Thread.sleep(5000)

        // Look for any result in list
        composeTestRule.onAllNodesWithTag("breed_search_line")[0].assertExists()
    }

    @Test
    fun removeFromFavorites() {
        // Start the app
        composeTestRule.setContent {
            // Create a NavHostController that handles the adding of the ComposeNavigator and DialogNavigator.
            val navController = rememberNavController()

            // Call the entry point composable function
            Home(navController)
        }

        // Wait for loadings to end
        Thread.sleep(5000)

        // Turn card on a Favorite
        composeTestRule.onAllNodesWithTag("favorite_button")[0].performClick()

        // Click on the Favorites button
        composeTestRule.onNodeWithTag("bottom_navigation_bar_favorites").performClick()

        // Wait for loadings to end
        Thread.sleep(5000)

        // Look for any result in list
        composeTestRule.onAllNodesWithTag("favorite_card")[0].assertExists()

        // Remove from Favorites
        composeTestRule.onAllNodesWithTag("favorite_button")[0].performClick()

        // Go back to the breeds list
        composeTestRule.onNodeWithTag("bottom_navigation_bar_breeds").performClick()

        // Go back to the favorites list
        composeTestRule.onNodeWithTag("bottom_navigation_bar_favorites").performClick()

        // Favorite should not exist
        composeTestRule.onAllNodesWithTag("favorite_card")[0].assertDoesNotExist()
    }
}