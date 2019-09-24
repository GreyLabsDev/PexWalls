package com.greylabsdev.pexwalls.common

import org.koin.dsl.module

val resourceManagerModule = module {

    single { ResourceManager(get()) }

}