package com.greylabsdev.pexwalls.data.network

import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val STANDARD_PAGE_SIZE = 15

interface PexelsApi {

    @GET("v1/search")
    fun searchPhotoByQuery(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = STANDARD_PAGE_SIZE
    ): Observable<SearchResultDto>

    @GET("v1/search")
    fun searchPhotoByQuerySingle(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = STANDARD_PAGE_SIZE
    ): Single<SearchResultDto>

    @GET("v1/curated")
    fun getCuratedPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = STANDARD_PAGE_SIZE
    ): Call<SearchResultDto>

    @GET("photos/{id}pexels-photo-{id}.jpeg")
    fun getPhotoById(
        @Path("id") id: String,
        @Query("h") height: Int,
        @Query("w") width: Int
    )

    // For coroutines

    @GET("v1/search")
    fun searchPhotoByQueryCall(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = STANDARD_PAGE_SIZE
    ): Call<SearchResultDto>

}