package com.greylabsdev.pexwalls.data.repository

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.db.AppDatabase
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import com.greylabsdev.pexwalls.data.network.PexelsApi
import com.greylabsdev.pexwalls.domain.repository.IRepository
import io.reactivex.Completable
import io.reactivex.Single

class Repository(
    private val localDataSource: IDataSource,
    private val remoteDataSource: IDataSource
) : IRepository {

    override suspend fun getCuratedPhotos(page: Int, perPage: Int): SearchResultDto? {
        return remoteDataSource.getCuratedPhotos(page, perPage)
    }

    override suspend fun searchPhotos(query: String, page: Int, perPage: Int): SearchResultDto? {
        return remoteDataSource.searchPhotos(query, page, perPage)
    }

    override suspend fun addPhotoToFavorites(photoEntity: PhotoDbEntity) {
        localDataSource.addPhotoToFavorites(photoEntity)
    }

    override suspend fun removePhotoFromFavorites(photoEntity: PhotoDbEntity) {
        localDataSource.removePhotoFromFavorites(photoEntity)
    }

    override suspend fun checkIfPhotoInFavorites(id: Int): Boolean {
        return localDataSource.checkIfPhotoInFavorites(id)
    }

    override suspend fun getPhotoById(id: Int): PhotoDbEntity {
        return localDataSource.getPhotoById(id)
    }

    override suspend fun getAllFavoritePhotos(): List<PhotoDbEntity> {
        return localDataSource.getAllPhotos()
    }
}
