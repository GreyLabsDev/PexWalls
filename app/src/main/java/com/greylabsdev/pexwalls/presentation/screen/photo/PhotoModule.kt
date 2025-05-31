package com.greylabsdev.pexwalls.presentation.screen.photo

import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val photoModule = module {
    viewModel { (photoModel: PhotoModel) -> PhotoViewModel(get(), get(), photoModel) }
}
