package com.greylabsdev.pexwalls.domain.repository

import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import io.reactivex.Observable

interface IRepository  {
    fun searchPhotos(query: String, page: Int, perPage: Int): Observable<SearchResultDto>
    fun getCuratedPhotos(page: Int, perPage: Int): Observable<SearchResultDto>
}