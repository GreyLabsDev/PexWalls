package com.greylabsdev.pexwalls.presentation.view.navigation

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.ext.measureWidth
import kotlinx.android.synthetic.main.view_navigation_button.view.*

class NavigationButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    title: String? = null,
    @DrawableRes iconRes: Int? = null,
    var onClickAction: (() -> Unit)? = null
): LinearLayout(context, attrs, defStyleAttr) {

    private val titleWidth: Int by lazy { this.btn_title_tv.measureWidth() }

    init {
        View.inflate(context, R.layout.view_navigation_button, this)
        title?.let { this.btn_title_tv.text = it }
        iconRes?.let { this.btn_icon_iv.setImageResource(it) }
    }

    fun swapIn() {
        this.btn_title_tv.swapInBySize()
    }

    fun swapOut() {
        this.btn_title_tv.swapOutBySize(swapWidth = titleWidth)
    }

    private fun View.swapInBySize(animDuration: Long = 200) {
        val animator = ValueAnimator.ofInt(this.measuredWidth, 0)
        animator.addUpdateListener {
            val value = animator.animatedValue as Int
            val layoutParams = this.layoutParams as ViewGroup.LayoutParams
            layoutParams.width = value
            this.layoutParams = layoutParams
        }
        animator.apply {
            duration = animDuration
            interpolator = AccelerateInterpolator()
        }
        animator.start()
    }

    private fun View.swapOutBySize(animDuration: Long = 200, swapWidth: Int = 100) {
        val animator = ValueAnimator.ofInt(0, swapWidth)
        animator.addUpdateListener {
            val value = animator.animatedValue as Int
            val layoutParams = this.layoutParams as ViewGroup.LayoutParams
            layoutParams.width = value
            this.layoutParams = layoutParams
        }
        animator.apply {
            duration = animDuration
            interpolator = AccelerateInterpolator()
        }
        animator.start()
    }
}