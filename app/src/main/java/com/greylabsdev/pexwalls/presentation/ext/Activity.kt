package com.greylabsdev.pexwalls.presentation.ext

import android.app.Activity

/**
 * Getting serializable extras from intent by lazy. Define generic type for type casting
 *
 * @property key - intent extra key
 *
 * special thanks to Denis Performer (https://github.com/Perfomer)
 */
fun <T> Activity.argSerializable(key: String) = lazy { intent!!.getSerializableExtra(key)!! as T }
fun <T> Activity.argSerializableNullable(key: String) = lazy { intent?.getSerializableExtra(key) as T }