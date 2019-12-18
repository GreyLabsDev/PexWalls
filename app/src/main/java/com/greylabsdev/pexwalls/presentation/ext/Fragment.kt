package com.greylabsdev.pexwalls.presentation.ext

import androidx.fragment.app.Fragment

fun <T> Fragment.argSerializable(key: String) = lazy { arguments!!.getSerializable(key) as T }
