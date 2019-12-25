package com.greylabsdev.pexwalls.data.datasource.local

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.db.AppDatabase
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.Exception

class LocalDataSource(private val appDatabase: AppDatabase) : IDataSource {

    override suspend fun getCuratedPhotos(page: Int, perPage: Int): SearchResultDto? {
        throw Exception("Method only for RemoteDataSource realization")
    }

    override suspend fun searchPhotos(query: String, page: Int, perPage: Int): SearchResultDto? {
        throw Exception("Method only for RemoteDataSource realization")
    }

    override suspend fun addPhotoToFavorites(photoEntity: PhotoDbEntity) {
        appDatabase.photoDao().insert(photoEntity)
    }

    override suspend fun removePhotoFromFavorites(photoEntity: PhotoDbEntity) {
        appDatabase.photoDao().delete(photoEntity)
    }

    override suspend fun checkIfPhotoInFavorites(id: Int): Boolean {
        return appDatabase.photoDao().getById(id).isNotEmpty()
    }

    override suspend fun getPhotoById(id: Int): PhotoDbEntity {
        return appDatabase.photoDao().getById(id).first()
    }

    override suspend fun getAllPhotos(): List<PhotoDbEntity> {
        return appDatabase.photoDao().getAll()
    }
}
