package com.greylabsdev.pexwalls.data.prefs

import org.koin.dsl.module

val prefsModule = module {
    single { AppPrefs(get()) }
}