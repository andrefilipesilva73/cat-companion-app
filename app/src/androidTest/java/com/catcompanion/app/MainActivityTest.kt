package com.catcompanion.app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.catcompanion.app.ui.theme.CatCompanionAppTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule

/**
 * Main Activity Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHomeScreenLanding() {
        // Start the app
        composeTestRule.setContent {
            // Call the entry point composable function
            CatCompanionAppTheme {
                // Call the Main composable function for preview
                Main()
            }
        }

        // Look for the main Nav Host
        composeTestRule.onNodeWithTag("main_nav_host").assertExists()

        // Make sure that Home is loaded
        composeTestRule.onNodeWithTag("home_screen").assertExists()
    }
}