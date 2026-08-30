package com.greylabsdev.pexwalls.data.datasource

import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.SearchResultDto

internal class FakeDataSource : IDataSource {

    var search: SearchResultDto? = null
    var curated: SearchResultDto? = null
    val favorites = mutableListOf<PhotoDbEntity>()

    var lastSearchQuery: String? = null
    var lastSearchPage: Int? = null
    var lastSearchPerPage: Int? = null
    var lastCuratedPage: Int? = null
    var lastCuratedPerPage: Int? = null

    override suspend fun searchPhotos(query: String, page: Int, perPage: Int): SearchResultDto? {
        lastSearchQuery = query
        lastSearchPage = page
        lastSearchPerPage = perPage
        return search
    }

    override suspend fun getCuratedPhotos(page: Int, perPage: Int): SearchResultDto? {
        lastCuratedPage = page
        lastCuratedPerPage = perPage
        return curated
    }

    override suspend fun addPhotoToFavorites(photoEntity: PhotoDbEntity) {
        favorites.removeAll { it.id == photoEntity.id }
        favorites.add(photoEntity)
    }

    override suspend fun removePhotoFromFavorites(photoEntity: PhotoDbEntity) {
        favorites.removeAll { it.id == photoEntity.id }
    }

    override suspend fun checkIfPhotoInFavorites(id: Int): Boolean =
        favorites.any { it.id == id }

    override suspend fun getPhotoById(id: Int): PhotoDbEntity =
        favorites.first { it.id == id }

    override suspend fun getAllPhotos(): List<PhotoDbEntity> = favorites.toList()
}
