package com.greylabsdev.pexwalls.presentation.screen.curatedphotos

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val curatedPhotosModule = module {
    viewModel { CuratedPhotosViewModel(get()) }
}
