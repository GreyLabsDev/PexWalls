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

    @Test
    fun emptyDatabaseReturnsEmptyLists() {
        assertTrue(dao.getAll().isEmpty())
        assertTrue(dao.getAllPhotos().isEmpty())
        assertTrue(dao.getById(1).isEmpty())
    }

    @Test
    fun getAllAndGetAllPhotosReturnSameRows() {
        dao.insert(PhotoFixtures.photoDbEntity(id = 1))
        dao.insert(PhotoFixtures.photoDbEntity(id = 2))
        assertEquals(dao.getAll().map { it.id }, dao.getAllPhotos().map { it.id })
        assertEquals(2, dao.getAll().size)
    }

    @Test
    fun getByIdDoesNotReturnOtherIds() {
        dao.insert(PhotoFixtures.photoDbEntity(id = 10))
        dao.insert(PhotoFixtures.photoDbEntity(id = 11))
        val found = dao.getById(10)
        assertEquals(1, found.size)
        assertEquals(10, found.first().id)
        assertEquals("Ada", found.first().photographer)
        assertTrue(dao.getById(99).isEmpty())
    }

    @Test
    fun insertPersistsAllColumns() {
        val entity = PhotoFixtures.photoDbEntity(id = 20, byScreenResolution = "screen-20")
        dao.insert(entity)
        val stored = dao.getById(20).first()
        assertEquals(entity.normalPhotoUrl, stored.normalPhotoUrl)
        assertEquals(entity.bigPhotoUrl, stored.bigPhotoUrl)
        assertEquals("screen-20", stored.byScreenResolution)
        assertEquals(entity.photographerUrl, stored.photographerUrl)
        assertEquals(entity.width, stored.width)
        assertEquals(entity.height, stored.height)
    }
}
