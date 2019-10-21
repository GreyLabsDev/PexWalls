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
 * Getting Density-independent Pixels from clean pixels
 *
 * @property pixValue - size in clean pixels
 */
fun Context.pixToDp(pixValue: Int): Int {
    val density = this.resources
        .displayMetrics
        .density
    return (pixValue / density).toInt()
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

/**
 * Getting navigation bar height
 * */
fun Context.getNavigationBarHeight(): Int {
    val resId = this.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resId > 0) this.resources.getDimensionPixelSize(resId)
           else 0
}

fun Context.windowManager(): WindowManager {
    return this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
}