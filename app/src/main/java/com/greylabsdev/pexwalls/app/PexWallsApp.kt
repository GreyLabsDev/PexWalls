package com.greylabsdev.pexwalls.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.greylabsdev.pexwalls.common.resourceManagerModule
import com.greylabsdev.pexwalls.data.datasource.dataSourceModule
import com.greylabsdev.pexwalls.data.db.databaseModule
import com.greylabsdev.pexwalls.data.network.networkModule
import com.greylabsdev.pexwalls.data.prefs.prefsModule
import com.greylabsdev.pexwalls.data.repository.repositoryModule
import com.greylabsdev.pexwalls.domain.tools.toolsModule
import com.greylabsdev.pexwalls.domain.usecase.useCaseModule
import com.greylabsdev.pexwalls.presentation.screen.categoryphotos.categoryPhotosModule
import com.greylabsdev.pexwalls.presentation.screen.curatedphotos.curatedPhotosModule
import com.greylabsdev.pexwalls.presentation.screen.favorites.favoritesModule
import com.greylabsdev.pexwalls.presentation.screen.home.homeModule
import com.greylabsdev.pexwalls.presentation.screen.photo.photoModule
import com.greylabsdev.pexwalls.presentation.screen.search.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PexWallsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@PexWallsApp)
            modules(
                listOf(
                    // data
                    databaseModule,
                    dataSourceModule,
                    networkModule,
                    prefsModule,
                    repositoryModule,

                    // tools
                    resourceManagerModule,
                    toolsModule,

                    // screens
                    useCaseModule,
                    categoryPhotosModule,
                    curatedPhotosModule,
                    homeModule,
                    searchModule,
                    photoModule,
                    favoritesModule
                )
            )
        }
    }
}
