package com.catcompanion.app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Main Activity Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BreedDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun enterScreen() {
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

        // Wait for loadings to end
        Thread.sleep(5000)
    }

    private fun markAsFavorite() {
        // Enter on the Screen
        enterScreen()

        // Mark as Favorite
        composeTestRule.onNodeWithTag("breed_detail_screen_favorite_button").performClick()
    }

    @Test
    fun testDetailsList() {
        // Enter on the Screen
        enterScreen()

        // Check all fields
        // Name
        composeTestRule.onNodeWithTag("breed_detail_screen_name").assertExists()

        // Image
        composeTestRule.onNodeWithTag("breed_detail_screen_image").assertExists()

        // Form
        composeTestRule.onNodeWithTag("breed_detail_screen_form_name_title").assertExists()
        composeTestRule.onNodeWithTag("breed_detail_screen_form_name_value").assertExists()
        composeTestRule.onNodeWithTag("breed_detail_screen_form_origin_title").assertExists()
        composeTestRule.onNodeWithTag("breed_detail_screen_form_origin_value").assertExists()
        composeTestRule.onNodeWithTag("breed_detail_screen_form_temperament_title").assertExists()
        composeTestRule.onNodeWithTag("breed_detail_screen_form_temperament_value").assertExists()
        composeTestRule.onNodeWithTag("breed_detail_screen_form_description_title").assertExists()
        composeTestRule.onNodeWithTag("breed_detail_screen_form_description_value").assertExists()
        composeTestRule.onNodeWithTag("breed_detail_screen_form_life_span_title").assertExists()
        composeTestRule.onNodeWithTag("breed_detail_screen_form_life_span_value").assertExists()
    }

    @Test
    fun testMarkAsFavorite() {
        // Mark as Favorite
        markAsFavorite()

        // Go back to Home
        composeTestRule.onNodeWithTag("breed_detail_screen_back_button").performClick()

        // Go back to the Favorites Screen
        composeTestRule.onNodeWithTag("bottom_navigation_bar_favorites").performClick()

        // Wait for loadings to end
        Thread.sleep(1000)

        // Look for any result in list
        composeTestRule.onAllNodesWithTag("favorite_card")[0].assertExists()
    }

    @Test
    fun testRemoveFromFavorites() {
        // Mark as Favorite
        markAsFavorite()

        // Remove it from Favorites
        composeTestRule.onNodeWithTag("breed_detail_screen_favorite_button").performClick()

        // Go back to Home
        composeTestRule.onNodeWithTag("breed_detail_screen_back_button").performClick()

        // Go back to the Favorites Screen
        composeTestRule.onNodeWithTag("bottom_navigation_bar_favorites").performClick()

        // Wait for loadings to end
        Thread.sleep(1000)

        // Look for any result in list
        composeTestRule.onAllNodesWithTag("favorite_card")[0].assertDoesNotExist()
    }
}