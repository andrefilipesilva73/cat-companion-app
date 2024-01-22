package com.catcompanion.app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.catcompanion.app.view.Home
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
    fun testBottomNavigationBar() {
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
}