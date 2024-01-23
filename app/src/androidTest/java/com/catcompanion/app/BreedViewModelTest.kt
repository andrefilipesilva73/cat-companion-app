package com.catcompanion.app

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.catcompanion.app.db.AppDatabase
import com.catcompanion.app.repository.BreedRepository
import com.catcompanion.app.viewmodel.BreedViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Main Activity Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BreedViewModelTest {
    private lateinit var viewModel: BreedViewModel

    @Before
    fun initViewModel() {
        // Context
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Create View model
        viewModel = BreedViewModel(
            BreedRepository(
                AppDatabase.getInstance(context).breedDao()
            )
        )
    }

    @Test
    fun viewModelNotNull() {
        assertNotNull(viewModel)
    }

    @Test
    fun performSearch() {
        // Execute
        viewModel.onQueryChange("American")

        // Wait for loadings to end
        Thread.sleep(5000)

        // Check size
        assertTrue(viewModel.searchResultsList.value.isNotEmpty())
    }
}