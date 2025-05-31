package com.greylabsdev.pexwalls.presentation.screen.favorites

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {

    viewModel { FavoritesViewModel(get()) }
}
