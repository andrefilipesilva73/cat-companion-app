package com.catcompanion.app

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.catcompanion.app.db.AppDatabase
import com.catcompanion.app.db.BreedDao
import com.catcompanion.app.model.Breed
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Main Activity Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class BreedDaoTest {
    private lateinit var dao: BreedDao

    // Create Breed
    private val breed1 = Breed(
        "1",
        "Amazing Cat",
        "temperament",
        "origin",
        "description",
        "life_span",
        "",
        true
    );
    private val breed2 = Breed(
        "2",
        "Super Cat",
        "temperament",
        "origin",
        "description",
        "life_span",
        "",
        false
    );

    @Before
    fun initDao() {
        // Context
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Create DAO
        dao = AppDatabase.getInstance(context).breedDao()
    }

    @Test
    fun testA_insert() = runTest {
        // Insert
        dao.insert(breed1)

        // Get all
        val results = dao.getAll()

        // Evaluate
        assertNotEquals(0, results.size)
    }

    @Test
    fun testB_update() = runTest {
        // Insert
        dao.insert(breed2)

        // Change
        breed2.origin = "update_test"

        // Update
        dao.update(breed2)

        // Get all
        val results = dao.getAll()

        // Find element by id
        val element = results.filter { breed -> breed.id == breed2.id }

        // Evaluate
        assertNotNull(element)
        assertEquals("update_test", element[0].origin)
    }

    @Test
    fun testC_insertOrUpdate() = runTest {
        // Insert
        dao.insertOrUpdate(breed1)

        // Get all
        val results = dao.getAll()

        // Evaluate
        assertNotEquals(0, results.size)
    }

    @Test
    fun testD_insertOrUpdateMany() = runTest {
        // Insert
        dao.insertOrUpdateMany(listOf(breed1, breed2))

        // Get all
        val results = dao.getAll()

        // Evaluate
        assertTrue(results.size >= 2)
    }

    @Test
    fun testF_getById() = runTest {
        // Insert
        dao.insertOrUpdate(breed1)

        // Get all
        val result = dao.getById(breed1.id)

        // Evaluate
        assertNotNull(result)
    }

    @Test
    fun testG_getPagedBreeds() = runTest {
        // Insert
        dao.insertOrUpdateMany(listOf(breed1, breed2))

        // Get all
        val results = dao.getPagedBreeds(1, 0)

        // Evaluate
        assertTrue(results.size == 1)
    }

    @Test
    fun testH_searchBreedsByName() = runTest {
        // Insert
        dao.insertOrUpdateMany(listOf(breed1, breed2))

        // Get all
        val results = dao.searchBreedsByName("Super")

        // Evaluate
        assertTrue(results.size == 1)
    }

    @Test
    fun testI_getPagedFavoriteBreeds() = runTest {
        // Insert
        dao.insertOrUpdateMany(listOf(breed1, breed2))

        // Get all
        val results = dao.getPagedFavoriteBreeds(1, 0)

        // Evaluate
        assertTrue(results.size == 1)
    }

    @Test
    fun testJ_isFavorite() = runTest {
        // Insert
        dao.insertOrUpdateMany(listOf(breed1, breed2))

        // Get all
        val result = dao.isFavorite(breed1.id)

        // Evaluate
        assertTrue(result)
    }

    @Test
    fun testK_searchFavoritesByName() = runTest {
        // Insert
        dao.insertOrUpdateMany(listOf(breed1, breed2))

        // Get all
        val results = dao.searchFavoritesByName("Amazing")

        // Evaluate
        assertTrue(results.size == 1)
    }
}