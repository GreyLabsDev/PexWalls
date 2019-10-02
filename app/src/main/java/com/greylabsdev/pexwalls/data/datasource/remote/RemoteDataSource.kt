package com.greylabsdev.pexwalls.data.datasource.remote

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import com.greylabsdev.pexwalls.data.network.PexelsApi
import io.reactivex.Observable
import io.reactivex.Single

class RemoteDataSource(private val api: PexelsApi) : IDataSource {
    override fun searchPhotosSingle(query: String, page: Int, perPage: Int): Single<SearchResultDto> {
        return api.searchPhotoByQuerySingle(query,page, perPage)
    }

    override fun searchPhotos(query: String, page: Int, perPage: Int): Observable<SearchResultDto> {
        return api.searchPhotoByQuery(query,page, perPage)
    }

    override fun getCuratedPhotos(page: Int, perPage: Int): Observable<SearchResultDto> {
        return api.getCuratedPhotos(page, perPage)
    }
}