package com.greylabsdev.pexwalls.data.repository

import com.greylabsdev.pexwalls.PhotoFixtures
import com.greylabsdev.pexwalls.data.datasource.FakeDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RepositoryTest {

    private val local = FakeDataSource()
    private val remote = FakeDataSource()
    private val repository = Repository(local, remote)

    @Test
    fun `curated photos come from remote not local`() = runBlocking {
        remote.curated = PhotoFixtures.searchResultDto(page = 2)
        local.curated = PhotoFixtures.searchResultDto(
            photos = listOf(PhotoFixtures.photoDto(id = 1)),
            page = 9
        )
        val result = repository.getCuratedPhotos(page = 2, perPage = 15)
        assertEquals(2, result?.page)
        assertEquals(2, remote.lastCuratedPage)
        assertEquals(15, remote.lastCuratedPerPage)
        assertNull(local.lastCuratedPage)
    }

    @Test
    fun `search photos come from remote with query`() = runBlocking {
        remote.search = PhotoFixtures.searchResultDto(totalResults = 8)
        val result = repository.searchPhotos("sea", page = 3, perPage = 10)
        assertEquals(8, result?.totalResults)
        assertEquals("sea", remote.lastSearchQuery)
        assertEquals(3, remote.lastSearchPage)
        assertEquals(10, remote.lastSearchPerPage)
        assertNull(local.lastSearchQuery)
    }

    @Test
    fun `null remote search is forwarded`() = runBlocking {
        remote.search = null
        assertNull(repository.searchPhotos("x", 1, 15))
    }

    @Test
    fun `favorites writes and reads go to local only`() = runBlocking {
        val entity = PhotoFixtures.photoDbEntity(id = 11)
        repository.addPhotoToFavorites(entity)
        assertTrue(repository.checkIfPhotoInFavorites(11))
        assertFalse(remote.checkIfPhotoInFavorites(11))
        assertEquals(11, repository.getPhotoById(11).id)
        assertEquals(1, repository.getAllFavoritePhotos().size)
        repository.removePhotoFromFavorites(entity)
        assertFalse(repository.checkIfPhotoInFavorites(11))
        assertTrue(repository.getAllFavoritePhotos().isEmpty())
    }
}
