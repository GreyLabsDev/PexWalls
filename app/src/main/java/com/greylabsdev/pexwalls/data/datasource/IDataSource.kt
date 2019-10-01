package com.greylabsdev.pexwalls.data.datasource

import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import io.reactivex.Observable

interface IDataSource {
    fun searchPhotos(query: String, page: Int, perPage: Int): Observable<SearchResultDto>
    fun getCuratedPhotos(page: Int, perPage: Int): Observable<SearchResultDto>
}