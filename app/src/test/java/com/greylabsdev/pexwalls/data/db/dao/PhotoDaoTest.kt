package com.greylabsdev.pexwalls.data.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.greylabsdev.pexwalls.PhotoFixtures
import com.greylabsdev.pexwalls.data.db.AppDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class PhotoDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: PhotoDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = db.photoDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetAll() {
        val entity = PhotoFixtures.photoDbEntity(id = 3)
        dao.insert(entity)
        val all = dao.getAll()
        assertEquals(1, all.size)
        assertEquals(3, all.first().id)
    }

    @Test
    fun insertReplaceOnSameId() {
        dao.insert(PhotoFixtures.photoDbEntity(id = 3, byScreenResolution = "a"))
        dao.insert(PhotoFixtures.photoDbEntity(id = 3, byScreenResolution = "b"))
        assertEquals("b", dao.getById(3).first().byScreenResolution)
    }

    @Test
    fun deleteRemovesRow() {
        val entity = PhotoFixtures.photoDbEntity(id = 4)
        dao.insert(entity)
        dao.delete(entity)
        assertTrue(dao.getById(4).isEmpty())
        assertTrue(dao.getAllPhotos().isEmpty())
    }
}
