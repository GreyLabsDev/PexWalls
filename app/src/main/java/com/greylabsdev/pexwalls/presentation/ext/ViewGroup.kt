package com.greylabsdev.pexwalls.presentation.ext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes resource: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View {
    return context.inflate(resource, root, attachToRoot)
}

fun Context.inflate(@LayoutRes resource: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View {
    val inflater = LayoutInflater.from(this)
    return inflater.inflate(resource, root, attachToRoot)
}
