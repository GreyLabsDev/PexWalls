package com.greylabsdev.pexwalls.data.datasource.local

import com.greylabsdev.pexwalls.data.datasource.IDataSource
import com.greylabsdev.pexwalls.data.db.AppDatabase

class LocalDataSource(private val appDatabase: AppDatabase) : IDataSource {
}