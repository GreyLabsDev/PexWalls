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
        assertEquals(photo.normalPhotoUrl, listed.first().normalPhotoUrl)
        assertEquals(photo.bigPhotoUrl, listed.first().bigPhotoUrl)
        assertEquals(photo.photographer, listed.first().photographer)
    }

    @Test
    fun `remove drops favorite`() = runBlocking {
        val photo = PhotoFixtures.photoFavoriteEntity(id = 8)
        useCase.addPhotoToFavorites(photo)
        useCase.removePhotoFromFavorites(photo)
        assertFalse(useCase.checkIfPhotoInFavorites(8))
        assertTrue(useCase.getFavoritePhotos().isEmpty())
    }

    @Test
    fun `empty repository yields empty list and false check`() = runBlocking {
        assertTrue(useCase.getFavoritePhotos().isEmpty())
        assertFalse(useCase.checkIfPhotoInFavorites(1))
    }

    @Test
    fun `adding same id replaces previous row`() = runBlocking {
        useCase.addPhotoToFavorites(PhotoFixtures.photoFavoriteEntity(id = 5))
        useCase.addPhotoToFavorites(PhotoFixtures.photoFavoriteEntity(id = 5))
        assertEquals(1, useCase.getFavoritePhotos().size)
        assertEquals(1, repository.favorites.size)
    }

    @Test
    fun `two different ids both listed`() = runBlocking {
        useCase.addPhotoToFavorites(PhotoFixtures.photoFavoriteEntity(id = 1))
        useCase.addPhotoToFavorites(PhotoFixtures.photoFavoriteEntity(id = 2))
        val ids = useCase.getFavoritePhotos().map { it.id }.toSet()
        assertEquals(setOf(1, 2), ids)
        assertTrue(useCase.checkIfPhotoInFavorites(1))
        assertTrue(useCase.checkIfPhotoInFavorites(2))
        assertFalse(useCase.checkIfPhotoInFavorites(3))
    }

    @Test
    fun `remove of missing id is a no-op`() = runBlocking {
        useCase.addPhotoToFavorites(PhotoFixtures.photoFavoriteEntity(id = 1))
        useCase.removePhotoFromFavorites(PhotoFixtures.photoFavoriteEntity(id = 99))
        assertEquals(1, useCase.getFavoritePhotos().size)
        assertTrue(useCase.checkIfPhotoInFavorites(1))
    }
}
