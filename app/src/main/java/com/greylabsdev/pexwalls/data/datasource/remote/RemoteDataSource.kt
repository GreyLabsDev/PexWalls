package com.greylabsdev.pexwalls.data.datasource.remote

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.network.PexelsApi

class RemoteDataSource(private val api: PexelsApi) : IDataSource {
}