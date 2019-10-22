package com.greylabsdev.pexwalls.data.repository

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import com.greylabsdev.pexwalls.domain.repository.IRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class Repository(
    private val localDataSource: IDataSource,
    private val remoteDataSource: IDataSource
) : IRepository {

    override fun searchPhotos(query: String, page: Int, perPage: Int): Observable<SearchResultDto> {
        return remoteDataSource.searchPhotos(query, page, perPage)
    }

    override fun getCuratedPhotos(page: Int, perPage: Int): Observable<SearchResultDto> {
        return remoteDataSource.getCuratedPhotos(page, perPage)
    }

    override fun searchPhotosSingle(query: String, page: Int, perPage: Int): Single<SearchResultDto> {
        return remoteDataSource.searchPhotosSingle(query, page, perPage)
    }

    override fun addPhotoToFavorites(photoEntity: PhotoDbEntity): Completable {
        return localDataSource.addPhotoToFavorites(photoEntity)
    }

    override fun removePhotoFromFavorites(photoEntity: PhotoDbEntity): Completable {
        return localDataSource.removePhotoFromFavorites(photoEntity)
    }

    override fun removePhotoFromFavoritesById(id: Int): Completable {
        return localDataSource.removePhotoFromFavoritesById(id)
    }

    override fun checkIfPhotoInFavorites(id: Int): Single<Boolean> {
        return localDataSource.checkIfPhotoInFavorites(id)
    }

    override fun getPhotoById(id: Int): Single<PhotoDbEntity> {
        return localDataSource.getPhotoById(id)
    }

    override fun getAllFavoritePhotos(): Observable<List<PhotoDbEntity>> {
        return localDataSource.getAllPhotos()
    }

}