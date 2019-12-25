package com.greylabsdev.pexwalls.common

import android.content.Context

class ResManager(private val context: Context) {

    fun getString(id: Int): String {
        return context.resources.getString(id)
    }
}
