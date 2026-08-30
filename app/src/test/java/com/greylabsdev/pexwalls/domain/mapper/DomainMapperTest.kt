package com.greylabsdev.pexwalls.domain.mapper

import com.greylabsdev.pexwalls.PhotoFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class DomainMapperTest {

    @Test
    fun `mapToPhotoEntity copies dto fields and screen url`() {
        val dto = PhotoFixtures.photoDto()
        val screen = "https://example.com/by-screen.jpg"
        val entity = DomainMapper.mapToPhotoEntity(dto, screen)
        assertEquals(dto.id, entity.id)
        assertEquals(dto.height, entity.height)
        assertEquals(dto.width, entity.width)
        assertEquals(dto.photographer, entity.photographer)
        assertEquals(dto.photographerId, entity.photographerId)
        assertEquals(dto.src.large, entity.src.large)
        assertEquals(screen, entity.src.byScreenResolutionUrl)
    }

    @Test
    fun `favorite round trip through db entity preserves fields`() {
        val favorite = PhotoFixtures.photoFavoriteEntity()
        val db = DomainMapper.mapToDbEntity(favorite)
        val back = DomainMapper.mapToPhotoFavoriteEntity(db)
        assertEquals(favorite, back)
        assertEquals(favorite.byScreenResolutionUrl, db.byScreenResolution)
    }
}
