package com.greylabsdev.pexwalls.presentation.screen.favorites

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val favoritesModule = module {
    viewModelOf(::FavoritesViewModel)
}
