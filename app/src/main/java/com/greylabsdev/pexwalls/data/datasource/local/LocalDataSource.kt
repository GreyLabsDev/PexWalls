package com.greylabsdev.pexwalls.data.datasource.local

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.db.AppDatabase
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableDoOnEach
import java.lang.Exception

class LocalDataSource(private val appDatabase: AppDatabase) : IDataSource {
    override fun searchPhotos(query: String, page: Int, perPage: Int): Observable<SearchResultDto> {
        return ObservableDoOnEach.error(Exception("Method only for RemoteDataSource realization"))
    }

    override fun getCuratedPhotos(page: Int, perPage: Int): Observable<SearchResultDto> {
        return ObservableDoOnEach.error(Exception("Method only for RemoteDataSource realization"))
    }
}