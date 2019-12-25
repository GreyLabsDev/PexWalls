package com.greylabsdev.pexwalls.domain.tools

import android.content.Context
import android.util.DisplayMetrics
import com.greylabsdev.pexwalls.presentation.ext.windowManager

class ResolutionManager(private val context: Context) {

    val screenResolution: Resolution by lazy {
        val metrics = DisplayMetrics()
        context.windowManager().defaultDisplay.getMetrics(metrics)
        Resolution(
            metrics.widthPixels,
            metrics.heightPixels
        )
    }

    data class Resolution(
        val width: Int,
        val height: Int
    )
}
