package com.greylabsdev.pexwalls.presentation.ext

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Getting serializable extras from intent by lazy. Define generic type for type casting
 *
 * @property key - intent extra key
 *
 * special thanks to Denis Performer (https://github.com/Perfomer)
 */
fun <T> Activity.argSerializable(key: String) = lazy { intent!!.getSerializableExtra(key)!! as T }
fun <T> Activity.argSerializableNullable(key: String) = lazy { intent?.getSerializableExtra(key) as T }

/**
 * Forced hide system keyboard
 */
fun Activity.hideKeyboard() {
    val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    this.currentFocus?.let {focusedView ->
        inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}

val Activity.contentView: View
    get() = this.findViewById(android.R.id.content)
