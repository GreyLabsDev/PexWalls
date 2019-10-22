package com.greylabsdev.pexwalls.presentation.ext

import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes

fun View.setHeight(height: Int) {
    this.layoutParams.height = height
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    imageTintList = resources.getColorStateList(colorRes)
}

fun TextView.measureWidth(): Int {
    this.measure(0,0)
    return this.measuredWidth
}
