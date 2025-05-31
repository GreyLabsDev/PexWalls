package com.greylabsdev.pexwalls.presentation.screen.home

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}
