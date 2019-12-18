package com.greylabsdev.pexwalls

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented GoogleSans, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under GoogleSans.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.greylabsdev.pexwalls", appContext.packageName)
    }
}
