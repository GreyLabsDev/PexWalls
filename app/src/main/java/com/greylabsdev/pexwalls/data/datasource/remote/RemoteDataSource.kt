package com.greylabsdev.pexwalls.data.datasource.remote

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import com.greylabsdev.pexwalls.data.network.PexelsApi
import java.lang.Exception

class RemoteDataSource(private val api: PexelsApi) : IDataSource {

    override suspend fun searchPhotos(query: String, page: Int, perPage: Int): SearchResultDto? {
        val call = api.searchPhotoByQueryCall(query, page, perPage)
        val response = call.execute()
        return response.body()
    }

    override suspend fun getCuratedPhotos(page: Int, perPage: Int): SearchResultDto? {
        val call = api.getCuratedPhotos(page, perPage)
        val response = call.execute()
        return response.body()
    }

    override suspend fun addPhotoToFavorites(photoEntity: PhotoDbEntity) {
        throw Exception("Method only for LocalDataSource realization")
    }

    override suspend fun removePhotoFromFavorites(photoEntity: PhotoDbEntity) {
        throw Exception("Method only for LocalDataSource realization")
    }

    override suspend fun checkIfPhotoInFavorites(id: Int): Boolean {
        throw Exception("Method only for LocalDataSource realization")
    }

    override suspend fun getPhotoById(id: Int): PhotoDbEntity {
        throw Exception("Method only for LocalDataSource realization")
    }

    override suspend fun getAllPhotos(): List<PhotoDbEntity> {
        throw Exception("Method only for LocalDataSource realization")
    }
}
