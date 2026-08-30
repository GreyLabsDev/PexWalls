package com.greylabsdev.pexwalls.domain.repository

import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.SearchResultDto

class FakeRepository : IRepository {

    val favorites = mutableListOf<PhotoDbEntity>()
    var curated: SearchResultDto? = null
    var search: SearchResultDto? = null

    override suspend fun getCuratedPhotos(page: Int, perPage: Int): SearchResultDto? = curated

    override suspend fun searchPhotos(query: String, page: Int, perPage: Int): SearchResultDto? = search

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

    override suspend fun getAllFavoritePhotos(): List<PhotoDbEntity> = favorites.toList()
}
