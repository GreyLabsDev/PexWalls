package com.greylabsdev.pexwalls.presentation.mapper

import com.greylabsdev.pexwalls.PhotoFixtures
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class PresentationMapperTest {

    @Test
    fun `photo entity uses large and large2x as list and detail urls`() {
        val entity = PhotoFixtures.photoEntity(id = 7, byScreen = "https://cdn/screen.jpg")
        val model = PresentationMapper.mapToPhotoModel(entity)
        assertEquals(entity.id, model.id)
        assertEquals(entity.src.large, model.normalPhotoUrl)
        assertEquals(entity.src.large2x, model.bigPhotoUrl)
        assertEquals(entity.src.byScreenResolutionUrl, model.byScreenResolutionUrl)
        assertEquals(entity.photographer, model.photographer)
        assertEquals(entity.photographerUrl, model.photographerUrl)
        assertEquals(entity.width, model.width)
        assertEquals(entity.height, model.height)
        assertNotEquals(model.normalPhotoUrl, model.bigPhotoUrl)
    }

    @Test
    fun `favorite to model currently duplicates normal url as big url`() {
        val favorite = PhotoFixtures.photoFavoriteEntity()
        val model = PresentationMapper.mapToPhotoModel(favorite)
        assertEquals(favorite.id, model.id)
        assertEquals(favorite.normalPhotoUrl, model.normalPhotoUrl)
        assertEquals(favorite.normalPhotoUrl, model.bigPhotoUrl)
        assertNotEquals(favorite.bigPhotoUrl, model.bigPhotoUrl)
        assertEquals(favorite.byScreenResolutionUrl, model.byScreenResolutionUrl)
        assertEquals(favorite.photographer, model.photographer)
        assertEquals(favorite.photographerUrl, model.photographerUrl)
        assertEquals(favorite.width, model.width)
        assertEquals(favorite.height, model.height)
    }

    @Test
    fun `photo model to favorite keeps distinct big url`() {
        val model = PhotoFixtures.photoModel(id = 3)
        val favorite = PresentationMapper.mapToEntity(model)
        assertEquals(model.id, favorite.id)
        assertEquals(model.bigPhotoUrl, favorite.bigPhotoUrl)
        assertEquals(model.normalPhotoUrl, favorite.normalPhotoUrl)
        assertEquals(model.byScreenResolutionUrl, favorite.byScreenResolutionUrl)
        assertEquals(model.photographer, favorite.photographer)
        assertEquals(model.photographerUrl, favorite.photographerUrl)
        assertEquals(model.width, favorite.width)
        assertEquals(model.height, favorite.height)
        assertNotEquals(favorite.normalPhotoUrl, favorite.bigPhotoUrl)
    }

    @Test
    fun `entity to model to favorite does not round-trip big url through favorite mapper`() {
        val entity = PhotoFixtures.photoEntity()
        val model = PresentationMapper.mapToPhotoModel(entity)
        val favorite = PresentationMapper.mapToEntity(model)
        assertEquals(entity.src.large, favorite.normalPhotoUrl)
        assertEquals(entity.src.large2x, favorite.bigPhotoUrl)
        val modelFromFavorite = PresentationMapper.mapToPhotoModel(favorite)
        assertEquals(favorite.normalPhotoUrl, modelFromFavorite.bigPhotoUrl)
    }
}
