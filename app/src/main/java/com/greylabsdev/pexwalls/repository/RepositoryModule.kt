package com.greylabsdev.pexwalls.repository

import com.greylabsdev.pexwalls.domain.repository.IRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<IRepository> { Repository(get(named("local")), get(named("remote"))) }
}