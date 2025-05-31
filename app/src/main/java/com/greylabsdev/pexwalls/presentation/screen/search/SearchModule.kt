package com.greylabsdev.pexwalls.presentation.screen.search

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel { SearchViewModel(get()) }
}
