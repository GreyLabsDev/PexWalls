package com.greylabsdev.pexwalls.presentation.ext

import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.Navigation

fun View.setNavigationClickListener(@IdRes destinationId: Int) {
    this.setOnClickListener { Navigation.findNavController(this).navigate(destinationId) }
}