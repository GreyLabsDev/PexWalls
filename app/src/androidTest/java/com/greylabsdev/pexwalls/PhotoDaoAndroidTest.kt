package com.greylabsdev.pexwalls

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.greylabsdev.pexwalls.data.db.AppDatabase
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoDaoAndroidTest {

    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertRoundTrip() {
        val entity = PhotoDbEntity(
            id = 11,
            normalPhotoUrl = "n",
            bigPhotoUrl = "b",
            byScreenResolution = "s",
            photographer = "Ada",
            photographerUrl = "u",
            width = 1,
            height = 2
        )
        db.photoDao().insert(entity)
        assertEquals(11, db.photoDao().getById(11).first().id)
    }
}
