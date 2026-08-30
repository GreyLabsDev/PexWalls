package com.greylabsdev.pexwalls.domain.usecase

import com.greylabsdev.pexwalls.PhotoFixtures
import com.greylabsdev.pexwalls.domain.repository.FakeRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PhotoFavoritesUseCaseTest {

    private val repository = FakeRepository()
    private val useCase = PhotoFavoritesUseCase(repository)

    @Test
    fun `add then check then list`() = runBlocking {
        val photo = PhotoFixtures.photoFavoriteEntity(id = 5)
        useCase.addPhotoToFavorites(photo)
        assertTrue(useCase.checkIfPhotoInFavorites(5))
        val listed = useCase.getFavoritePhotos()
        assertEquals(1, listed.size)
        assertEquals(5, listed.first().id)
        assertEquals(photo.byScreenResolutionUrl, listed.first().byScreenResolutionUrl)
    }

    @Test
    fun `remove drops favorite`() = runBlocking {
        val photo = PhotoFixtures.photoFavoriteEntity(id = 8)
        useCase.addPhotoToFavorites(photo)
        useCase.removePhotoFromFavorites(photo)
        assertFalse(useCase.checkIfPhotoInFavorites(8))
        assertTrue(useCase.getFavoritePhotos().isEmpty())
    }
}
