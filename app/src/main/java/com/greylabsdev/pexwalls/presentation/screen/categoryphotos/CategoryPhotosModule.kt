package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryPhotosModule = module {
    viewModel { CategoryImagesViewModel(get()) }
}