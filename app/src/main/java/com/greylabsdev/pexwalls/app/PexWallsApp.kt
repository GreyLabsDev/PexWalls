package com.greylabsdev.pexwalls.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.greylabsdev.pexwalls.common.resourceManagerModule
import com.greylabsdev.pexwalls.data.datasource.dataSourceModule
import com.greylabsdev.pexwalls.data.network.networkModule
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
                    resourceManagerModule,
                    dataSourceModule,
                    networkModule
                )
            )
        }
    }
}