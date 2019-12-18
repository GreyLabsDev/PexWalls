package com.greylabsdev.pexwalls.data.prefs

import android.content.Context
import com.greylabsdev.pexwalls.data.ext.putPrimitiveValue

class AppPrefs(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun putSampleString(newString: String) {
        sharedPreferences.putPrimitiveValue("sample", newString)
    }

    fun getSampleString(): String? = sharedPreferences.getString("sample", null)

    companion object {
        private const val PREFS_NAME = "pixwall_prefs"
    }
}
