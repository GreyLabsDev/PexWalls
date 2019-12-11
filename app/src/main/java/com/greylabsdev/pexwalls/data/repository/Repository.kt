package com.greylabsdev.pexwalls.data.repository

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.db.AppDatabase
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import com.greylabsdev.pexwalls.data.network.PexelsApi
import com.greylabsdev.pexwalls.domain.repository.IRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class Repository(
    private val localDataSource: IDataSource,
    private val remoteDataSource: IDataSource,
    private val api: PexelsApi,
    private val appDatabase: AppDatabase
) : IRepository {

    override suspend fun getCuratedPhotos(page: Int, perPage: Int): SearchResultDto? {
        val call = api.getCuratedPhotos(page, perPage)
        val response = call.execute()
        return response.body()
    }

    override suspend fun searchPhotos(query: String, page: Int, perPage: Int): SearchResultDto? {
        val call = api.searchPhotoByQueryCall(query, page, perPage)
        val response = call.execute()
        return response.body()
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

    override suspend fun getAllFavoritePhotos(): List<PhotoDbEntity> {
        return appDatabase.photoDao().getAllPhotos()
    }

}