package com.greylabsdev.pexwalls.domain.usecase

import org.koin.dsl.module

val useCaseModule = module {

    factory { PhotoDisplayingUseCase(get()) }
    factory { PhotoDownloadingUseCase(get()) }
    factory { PhotoFavoritesUseCase(get()) }

}