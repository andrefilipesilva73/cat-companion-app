package com.catcompanion.app

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.catcompanion.app.db.AppDatabase
import com.catcompanion.app.model.Breed
import com.catcompanion.app.repository.BreedRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Main Activity Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BreedRepositoryTest {
    private lateinit var repository: BreedRepository

    // Create Breed
    val breed1 = Breed(
        "1",
        "Amazing Cat",
        "temperament",
        "origin",
        "description",
        "life_span",
        "",
        false
    );

    @Before
    fun initViewModel() {
        // Context
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Create Repository
        repository = BreedRepository(
            AppDatabase.getInstance(context).breedDao()
        )
    }

    @Test
    fun getBreedsByPages() = runTest {
        // Get
        val results = repository.getBreedsByPages(1, 0)

        // Evaluate
        assertEquals(1, results.size)
    }

    @Test
    fun getBreedsBySearch() = runTest {
        // Get
        val results = repository.getBreedsBySearch("American")

        // Evaluate
        assertNotEquals(0, results.size)
    }

    @Test
    fun getBreedById() = runTest {
        // Get
        val result = repository.getBreedById("ebur")

        // Evaluate
        assertNotNull(result)
    }

    @Test
    fun addAndRemoveBreedToOrFromFavorites() = runTest {
        val favoriteBreed = Breed(
            "2",
            "Favorite Cat",
            "temperament",
            "origin",
            "description",
            "life_span",
            "",
            false
        );

        // Add
        repository.addBreedToFavorites(favoriteBreed)

        // Wait for coroutines to end
        Thread.sleep(1000)

        // Get favorites
        val results = repository.getFavoritesByPages(1, 0)

        // Evaluate
        assertNotEquals(0, results.size)

        // Remove
        repository.removeBreedFromFavorites(favoriteBreed)

        // Wait for coroutines to end
        Thread.sleep(1000)

        // Get favorites
        val resultsAfterRemove = repository.getFavoritesByPages(1, 0)

        // Evaluate
        assertEquals(0, resultsAfterRemove.size)
    }

    @Test
    fun getFavoritesBySearch() = runTest {
        // Add
        repository.addBreedToFavorites(breed1)

        // Wait for coroutines to end
        Thread.sleep(1000)

        // Get favorites
        val results = repository.getFavoritesBySearch("Amazing")

        // Evaluate
        assertEquals(1, results.size)

        // Remove
        repository.removeBreedFromFavorites(breed1)

        // Wait for coroutines to end
        Thread.sleep(1000)

        // Get favorites
        val resultsAfterRemove = repository.getFavoritesByPages(1, 0)

        // Evaluate
        assertEquals(0, resultsAfterRemove.size)
    }

    @Test
    fun updateDatabase() = runTest {
        // Add
        repository.updateDatabase(listOf(breed1))

        // Read
        val results = repository.readDatabase()

        // Evaluate
        assertNotEquals(0, results.size)
    }
}