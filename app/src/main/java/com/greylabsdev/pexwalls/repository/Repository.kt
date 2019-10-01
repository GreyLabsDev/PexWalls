package com.greylabsdev.pexwalls.repository

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import com.greylabsdev.pexwalls.domain.repository.IRepository
import io.reactivex.Observable

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

}