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
        assertEquals(dto.url, entity.url)
        assertEquals(dto.photographer, entity.photographer)
        assertEquals(dto.photographerId, entity.photographerId)
        assertEquals(dto.photographerUrl, entity.photographerUrl)
        assertEquals(dto.src.landscape, entity.src.landscape)
        assertEquals(dto.src.large, entity.src.large)
        assertEquals(dto.src.large2x, entity.src.large2x)
        assertEquals(dto.src.medium, entity.src.medium)
        assertEquals(dto.src.original, entity.src.original)
        assertEquals(dto.src.portrait, entity.src.portrait)
        assertEquals(dto.src.small, entity.src.small)
        assertEquals(dto.src.tiny, entity.src.tiny)
        assertEquals(screen, entity.src.byScreenResolutionUrl)
    }

    @Test
    fun `mapToPhotoEntity keeps dto large url when screen url differs`() {
        val dto = PhotoFixtures.photoDto(
            src = PhotoFixtures.photoSrcDto(large = "https://cdn/large.jpg")
        )
        val entity = DomainMapper.mapToPhotoEntity(dto, "https://cdn/screen.jpg")
        assertEquals("https://cdn/large.jpg", entity.src.large)
        assertEquals("https://cdn/screen.jpg", entity.src.byScreenResolutionUrl)
    }

    @Test
    fun `favorite round trip through db entity preserves fields`() {
        val favorite = PhotoFixtures.photoFavoriteEntity(id = 99)
        val db = DomainMapper.mapToDbEntity(favorite)
        val back = DomainMapper.mapToPhotoFavoriteEntity(db)
        assertEquals(favorite, back)
        assertEquals(favorite.byScreenResolutionUrl, db.byScreenResolution)
        assertEquals(favorite.normalPhotoUrl, db.normalPhotoUrl)
        assertEquals(favorite.bigPhotoUrl, db.bigPhotoUrl)
        assertEquals(favorite.photographer, db.photographer)
        assertEquals(favorite.photographerUrl, db.photographerUrl)
        assertEquals(favorite.width, db.width)
        assertEquals(favorite.height, db.height)
    }

    @Test
    fun `mapToDbEntity uses byScreenResolutionUrl as byScreenResolution column`() {
        val favorite = PhotoFixtures.photoFavoriteEntity()
        val db = DomainMapper.mapToDbEntity(favorite)
        assertEquals(favorite.id, db.id)
        assertEquals("https://example.com/screen.jpg", db.byScreenResolution)
    }
}
