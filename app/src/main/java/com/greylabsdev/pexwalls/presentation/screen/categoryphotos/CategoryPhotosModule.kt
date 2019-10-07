package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryPhotosModule = module {
    viewModel { (photoCategory: PhotoCategory) -> CategoryPhotosViewModel(get(), photoCategory) }
}