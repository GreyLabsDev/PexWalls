package com.greylabsdev.pexwalls

import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.PhotoDto
import com.greylabsdev.pexwalls.data.dto.PhotoSrcDto
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import com.greylabsdev.pexwalls.domain.entity.PhotoEntity
import com.greylabsdev.pexwalls.domain.entity.PhotoFavoriteEntity
import com.greylabsdev.pexwalls.domain.entity.PhotoSrcEntity
import com.greylabsdev.pexwalls.presentation.model.PhotoModel

internal object PhotoFixtures {

    fun photoSrcDto(
        large: String = "https://example.com/large.jpg",
        large2x: String = "https://example.com/large2x.jpg"
    ) = PhotoSrcDto(
        landscape = "https://example.com/landscape.jpg",
        large = large,
        large2x = large2x,
        medium = "https://example.com/medium.jpg",
        original = "https://example.com/original.jpg",
        portrait = "https://example.com/portrait.jpg",
        small = "https://example.com/small.jpg",
        tiny = "https://example.com/tiny.jpg"
    )

    fun photoDto(
        id: Int = 42,
        src: PhotoSrcDto = photoSrcDto()
    ) = PhotoDto(
        height = 1200,
        id = id,
        photographer = "Ada",
        photographerId = 7L,
        photographerUrl = "https://example.com/ada",
        src = src,
        url = "https://example.com/photo/42",
        width = 800
    )

    fun photoDbEntity(
        id: Int = 42,
        byScreenResolution: String = "https://example.com/screen.jpg"
    ) = PhotoDbEntity(
        id = id,
        normalPhotoUrl = "https://example.com/normal.jpg",
        bigPhotoUrl = "https://example.com/big.jpg",
        byScreenResolution = byScreenResolution,
        photographer = "Ada",
        photographerUrl = "https://example.com/ada",
        width = 800,
        height = 1200
    )

    fun photoFavoriteEntity(
        id: Int = 42
    ) = PhotoFavoriteEntity(
        id = id,
        normalPhotoUrl = "https://example.com/normal.jpg",
        bigPhotoUrl = "https://example.com/big.jpg",
        byScreenResolutionUrl = "https://example.com/screen.jpg",
        photographer = "Ada",
        photographerUrl = "https://example.com/ada",
        width = 800,
        height = 1200
    )

    fun photoEntity(
        id: Int = 42,
        byScreen: String = "https://example.com/screen.jpg"
    ) = PhotoEntity(
        height = 1200,
        id = id,
        photographer = "Ada",
        photographerId = 7L,
        photographerUrl = "https://example.com/ada",
        src = PhotoSrcEntity(
            landscape = "https://example.com/landscape.jpg",
            large = "https://example.com/large.jpg",
            large2x = "https://example.com/large2x.jpg",
            medium = "https://example.com/medium.jpg",
            original = "https://example.com/original.jpg",
            portrait = "https://example.com/portrait.jpg",
            small = "https://example.com/small.jpg",
            tiny = "https://example.com/tiny.jpg",
            byScreenResolutionUrl = byScreen
        ),
        url = "https://example.com/photo/42",
        width = 800
    )

    fun photoModel(
        id: Int = 42
    ) = PhotoModel(
        id = id,
        normalPhotoUrl = "https://example.com/normal.jpg",
        bigPhotoUrl = "https://example.com/big.jpg",
        byScreenResolutionUrl = "https://example.com/screen.jpg",
        photographer = "Ada",
        photographerUrl = "https://example.com/ada",
        width = 800,
        height = 1200
    )

    fun searchResultDto(
        photos: List<PhotoDto> = listOf(photoDto()),
        page: Int = 1,
        perPage: Int = 15,
        totalResults: Int = photos.size,
        nextPage: String? = null
    ) = SearchResultDto(
        nextPage = nextPage,
        page = page,
        perPage = perPage,
        photos = photos,
        totalResults = totalResults
    )
}
