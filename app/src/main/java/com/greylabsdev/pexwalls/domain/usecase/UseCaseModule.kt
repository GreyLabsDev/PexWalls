package com.greylabsdev.pexwalls.domain.usecase

import org.koin.dsl.module

val useCaseModule = module {

    factory { SampleUseCase(get()) }
    factory { PhotoDisplayingUseCase(get()) }
    factory { PhotoDownloadingUseCase() }

}