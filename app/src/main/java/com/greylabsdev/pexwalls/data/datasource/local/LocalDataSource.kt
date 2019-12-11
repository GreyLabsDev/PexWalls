package com.greylabsdev.pexwalls.data.datasource.local

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.db.AppDatabase
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.internal.operators.observable.ObservableDoOnEach
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

    override fun addPhotoToFavorites(photoEntity: PhotoDbEntity): Completable {
        return appDatabase.photoDao().insert(photoEntity)
    }

    override fun removePhotoFromFavorites(photoEntity: PhotoDbEntity): Completable {
        return appDatabase.photoDao().delete(photoEntity)
    }

    override fun removePhotoFromFavoritesById(id: Int): Completable {
        return appDatabase.photoDao().deleteById(id)
    }

    override fun checkIfPhotoInFavorites(id: Int): Single<Boolean> {
        return appDatabase.photoDao().getById(id)
            .map { it.isNotEmpty() }
    }

    override fun getPhotoById(id: Int): Single<PhotoDbEntity> {
        return appDatabase.photoDao().getById(id).map { it.first() }
    }

    override fun getAllPhotos(): Observable<List<PhotoDbEntity>> {
        return appDatabase.photoDao().getAll()
    }
}