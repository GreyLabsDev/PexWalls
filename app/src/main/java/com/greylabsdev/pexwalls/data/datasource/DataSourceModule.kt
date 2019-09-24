package com.greylabsdev.pexwalls.data.datasource

import com.greylabsdev.pexwalls.data.datasource.local.LocalDataSource
import com.greylabsdev.pexwalls.data.datasource.remote.RemoteDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule = module {

    single<IDataSource>(named("local")) { LocalDataSource() }

    single<IDataSource>(named("remote")) { RemoteDataSource() }

}