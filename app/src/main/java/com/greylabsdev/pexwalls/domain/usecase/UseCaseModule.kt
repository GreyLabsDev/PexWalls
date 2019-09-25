package com.greylabsdev.pexwalls.domain.usecase

import org.koin.dsl.module

val useCaseModule = module {

    factory { SampleUseCase(get()) }

}