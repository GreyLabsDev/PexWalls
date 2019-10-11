package com.greylabsdev.pexwalls.presentation.ext

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Getting clean pixels from Density-independent Pixels
 *
 * @property dpValue - size in Density-independent Pixels
 */
fun Context.dpToPix(dpValue: Int): Float {
    return dpValue * this.resources.displayMetrics.density
}

/**
 * Getting device screen width in pixels
 */
fun Context.getScreenWidthInPixels(): Int {
    val metrics = DisplayMetrics()
    this.windowManager().defaultDisplay.getMetrics(metrics)
    return metrics.widthPixels
}

/**
 * Getting device screen height in pixels
 */
fun Context.getScreenHeightInPixels(): Int {
    val metrics = DisplayMetrics()
    this.windowManager().defaultDisplay.getMetrics(metrics)
    return metrics.heightPixels
}

fun Context.windowManager(): WindowManager {
    return this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
}