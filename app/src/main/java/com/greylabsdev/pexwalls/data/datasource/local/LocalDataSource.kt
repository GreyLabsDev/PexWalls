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

    override fun searchPhotosSingle(query: String, page: Int, perPage: Int): Single<SearchResultDto> {
        return Single.error(Exception("Method only for RemoteDataSource realization"))
    }

    override fun searchPhotos(query: String, page: Int, perPage: Int): Observable<SearchResultDto> {
        return Observable.error(Exception("Method only for RemoteDataSource realization"))
    }

//    override fun getCuratedPhotos(page: Int, perPage: Int): Observable<SearchResultDto> {
//        return Observable.error(Exception("Method only for RemoteDataSource realization"))
//    }

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

    override fun getAllPhotos(): Observable<List<PhotoDbEntity>> {
        return appDatabase.photoDao().getAll()
    }
}
