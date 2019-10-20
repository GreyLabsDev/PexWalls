package com.greylabsdev.pexwalls.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.greylabsdev.pexwalls.R

class NavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.view_navigation, this)
    }
}