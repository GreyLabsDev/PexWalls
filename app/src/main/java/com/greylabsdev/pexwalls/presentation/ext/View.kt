package com.greylabsdev.pexwalls.presentation.ext

import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes

fun View.setHeight(height: Int) {
    this.layoutParams.height = height
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    imageTintList = resources.getColorStateList(colorRes)
}