package com.catcompanion.app

import androidx.compose.ui.test.filter
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onSiblings
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.catcompanion.app.view.BreedsListScreen
import com.catcompanion.app.view.FavoritesListScreen
import com.catcompanion.app.view.Home
import kotlinx.coroutines.delay
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule

/**
 * Main Activity Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBottomNavigationBarNavigation() {
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
    fun testBreedListLoading() {
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
    fun testMarkAsFavoriteAndListLoading() {
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
    }

    @Test
    fun testBreedCardNavigationToDetail() {
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
}