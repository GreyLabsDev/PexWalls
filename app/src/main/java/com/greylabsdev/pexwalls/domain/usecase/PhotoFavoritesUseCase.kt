package com.greylabsdev.pexwalls.domain.usecase

import com.greylabsdev.pexwalls.domain.entity.PhotoFavoriteEntity
import com.greylabsdev.pexwalls.domain.mapper.DomainMapper
import com.greylabsdev.pexwalls.domain.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoFavoritesUseCase(
    private val repository: IRepository
) {

    suspend fun getFavoritePhotos(): List<PhotoFavoriteEntity> {
        return withContext(Dispatchers.IO) {
            repository.getAllFavoritePhotos().map { DomainMapper.mapToPhotoFavoriteEntity(it) }
        }
    }

    suspend fun addPhotoToFavorites(photo: PhotoFavoriteEntity) {
        withContext(Dispatchers.IO) {
            repository.addPhotoToFavorites(DomainMapper.mapToDbEntity(photo))
        }
    }

    suspend fun removePhotoFromFavorites(photo: PhotoFavoriteEntity) {
        withContext(Dispatchers.IO) {
            repository.removePhotoFromFavorites(DomainMapper.mapToDbEntity(photo))
        }
    }

    suspend fun checkIfPhotoInFavorites(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            repository.checkIfPhotoInFavorites(id)
        }
    }
}
