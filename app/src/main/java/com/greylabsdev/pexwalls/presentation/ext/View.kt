package com.greylabsdev.pexwalls.presentation.ext

import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.navigation.Navigation

fun View.setNavigationClickListener(@IdRes destinationId: Int) {
    this.setOnClickListener { Navigation.findNavController(this).navigate(destinationId) }
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    imageTintList = resources.getColorStateList(colorRes)
}