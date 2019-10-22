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
        this.btn_bg_iv.animate()
            .alpha(0f)
            .setDuration(DEFAULT_ALPHA_ANIM_DURATION)
            .start()
        this.btn_title_tv.swapInBySize(animDuration = DEFAULT_SCALE_ANIM_DURATION)
    }

    fun swapOut() {
        this.btn_bg_iv.animate()
            .alpha(1f)
            .setDuration(DEFAULT_ALPHA_ANIM_DURATION)
            .start()
        this.btn_title_tv.swapOutBySize(
            animDuration = DEFAULT_SCALE_ANIM_DURATION,
            swapWidth = titleWidth
        )
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

    private fun View.swapOutBySize(animDuration: Long = 200, swapWidth: Int = 0) {
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

    companion object {
        private const val DEFAULT_SCALE_ANIM_DURATION = 200L
        private const val DEFAULT_ALPHA_ANIM_DURATION = 350L
    }
}