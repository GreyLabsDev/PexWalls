package com.greylabsdev.pexwalls.data.datasource

import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.SearchResultDto

interface IDataSource {

    suspend fun searchPhotos(query: String, page: Int, perPage: Int): SearchResultDto?
    suspend fun getCuratedPhotos(page: Int, perPage: Int): SearchResultDto?

    suspend fun addPhotoToFavorites(photoEntity: PhotoDbEntity)
    suspend fun removePhotoFromFavorites(photoEntity: PhotoDbEntity)
    suspend fun checkIfPhotoInFavorites(id: Int): Boolean
    suspend fun getPhotoById(id: Int): PhotoDbEntity
    suspend fun getAllPhotos(): List<PhotoDbEntity>
}
