// Define the package where this class belongs
package etecode.asvaidosas.catcompanion

// Import necessary testing-related libraries for AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

// Import JUnit testing libraries
import org.junit.Test
import org.junit.runner.RunWith

// Import an assertion library for testing
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
// Use the AndroidJUnit4 test runner for instrumented tests
@RunWith(AndroidJUnit4::class)
// Declare the ExampleInstrumentedTest class
class ExampleInstrumentedTest {

    // Declare a test method named useAppContext
    @Test
    fun useAppContext() {
        // Context of the app under test.
        // Get the target context using InstrumentationRegistry
        /**
         * InstrumentationRegistry is a class provided by the Android Testing
         * Support Library that allows access to various instrumentation-related
         * information and services during Android instrumented tests.
         * Instrumented tests are tests that run on an Android device or emulator
         * and have access to the Android runtime environment.
         */
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        // Assert that the package name of the app under test matches the expected package name
        assertEquals("etecode.asvaidosas.catcompanion", appContext.packageName)
    }
}
