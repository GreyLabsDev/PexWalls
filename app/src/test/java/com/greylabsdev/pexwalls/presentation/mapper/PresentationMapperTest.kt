package com.greylabsdev.pexwalls.presentation.mapper

import com.greylabsdev.pexwalls.PhotoFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class PresentationMapperTest {

    @Test
    fun `photo entity uses large and large2x as list and detail urls`() {
        val entity = PhotoFixtures.photoEntity()
        val model = PresentationMapper.mapToPhotoModel(entity)
        assertEquals(entity.id, model.id)
        assertEquals(entity.src.large, model.normalPhotoUrl)
        assertEquals(entity.src.large2x, model.bigPhotoUrl)
        assertEquals(entity.src.byScreenResolutionUrl, model.byScreenResolutionUrl)
    }

    @Test
    fun `favorite to model currently duplicates normal url as big url`() {
        val favorite = PhotoFixtures.photoFavoriteEntity()
        val model = PresentationMapper.mapToPhotoModel(favorite)
        assertEquals(favorite.normalPhotoUrl, model.normalPhotoUrl)
        assertEquals(favorite.normalPhotoUrl, model.bigPhotoUrl)
        assertEquals(favorite.byScreenResolutionUrl, model.byScreenResolutionUrl)
    }

    @Test
    fun `photo model to favorite keeps distinct big url`() {
        val model = PhotoFixtures.photoModel()
        val favorite = PresentationMapper.mapToEntity(model)
        assertEquals(model.bigPhotoUrl, favorite.bigPhotoUrl)
        assertEquals(model.normalPhotoUrl, favorite.normalPhotoUrl)
    }
}
