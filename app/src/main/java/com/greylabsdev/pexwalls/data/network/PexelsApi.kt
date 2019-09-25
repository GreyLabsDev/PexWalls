package com.greylabsdev.pexwalls.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface PexelsApi  {

    @GET("search")
    fun searchPhotoByQuery(@Query("query") query: String,
                           @Query("page") page: Int,
                           @Query("per_page") perPage: Int): Observable

}