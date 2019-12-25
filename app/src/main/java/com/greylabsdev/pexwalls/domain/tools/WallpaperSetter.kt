package com.greylabsdev.pexwalls.domain.tools

import android.app.WallpaperManager
import android.content.Context
import java.net.URI

class WallpaperSetter(private val context: Context) {

    fun setWallpaperByImagePath(fileUri: URI) {
        val wallpaperManager = WallpaperManager.getInstance(context)
        val inputStream = fileUri.toURL().openStream()
        wallpaperManager.setStream(inputStream)
    }
}
