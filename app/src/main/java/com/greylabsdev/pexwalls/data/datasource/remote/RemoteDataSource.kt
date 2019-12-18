package com.greylabsdev.pexwalls.data.datasource.remote

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import com.greylabsdev.pexwalls.data.network.PexelsApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.Exception

class RemoteDataSource(private val api: PexelsApi) : IDataSource {

    override fun searchPhotosSingle(query: String, page: Int, perPage: Int): Single<SearchResultDto> {
        return api.searchPhotoByQuerySingle(query, page, perPage)
    }

    override fun searchPhotos(query: String, page: Int, perPage: Int): Observable<SearchResultDto> {
        return api.searchPhotoByQuery(query, page, perPage)
    }

//    override fun getCuratedPhotos(page: Int, perPage: Int): Observable<SearchResultDto> {
//        return api.getCuratedPhotos(page, perPage)
//    }

    override fun addPhotoToFavorites(photoEntity: PhotoDbEntity): Completable {
        return Completable.error(Exception("Method only for LocalDataSource realization"))
    }

    override fun removePhotoFromFavorites(photoEntity: PhotoDbEntity): Completable {
        return Completable.error(Exception("Method only for LocalDataSource realization"))
    }

    override fun removePhotoFromFavoritesById(id: Int): Completable {
        return Completable.error(Exception("Method only for LocalDataSource realization"))
    }

    override fun checkIfPhotoInFavorites(id: Int): Single<Boolean> {
        return Single.error(Exception("Method only for LocalDataSource realization"))
    }

    override fun getPhotoById(id: Int): Single<PhotoDbEntity> {
        return Single.error(Exception("Method only for LocalDataSource realization"))
    }

    override fun getAllPhotos(): Observable<List<PhotoDbEntity>> {
        return Observable.error(Exception("Method only for LocalDataSource realization"))
    }
}
