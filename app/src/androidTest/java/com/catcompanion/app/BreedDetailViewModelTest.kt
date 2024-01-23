package com.catcompanion.app

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.catcompanion.app.db.AppDatabase
import com.catcompanion.app.model.Breed
import com.catcompanion.app.repository.BreedRepository
import com.catcompanion.app.viewmodel.BreedDetailViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Main Activity Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BreedDetailViewModelTest {
    private lateinit var viewModel: BreedDetailViewModel

    @Before
    fun initViewModel() {
        // Context
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Create View model
        viewModel = BreedDetailViewModel(
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
    fun fetchBreedById() {
        // Create Breed
        val breed = Breed(
            "1",
            "Amazing Cat",
            "temperament",
            "origin",
            "description",
            "life_span",
            "",
            false
        );

        // Add to favorites
        viewModel.addBreedToFavorites(breed)

        // Execute
        val result = viewModel.fetchBreedById(breed.id)

        // Check size
        assertNotNull(result)
    }
}