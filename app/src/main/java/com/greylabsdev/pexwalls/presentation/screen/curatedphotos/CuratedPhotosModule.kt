package com.greylabsdev.pexwalls.presentation.screen.curatedphotos

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val curatedPhotosModule = module {
    viewModelOf(::CuratedPhotosViewModel)
}
