package com.greylabsdev.pexwalls.presentation.view.navigation

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.databinding.ViewNavigationButtonBinding
import com.greylabsdev.pexwalls.presentation.ext.measureWidth

class NavigationButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    title: String? = null,
    @DrawableRes iconRes: Int? = null,
    var onClickAction: (() -> Unit)? = null
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val titleWidth: Int by lazy { binding.btnTitleTv.measureWidth() }
    private val binding: ViewNavigationButtonBinding

    init {
        View.inflate(context, R.layout.view_navigation_button, this)
        binding = ViewNavigationButtonBinding.bind(this)
        title?.let { binding.btnTitleTv.text = it }
        iconRes?.let { binding.btnIconIv.setImageResource(it) }
        binding.root.setOnClickListener { onClickAction?.invoke() }
    }

    fun swapIn() {
        binding.btnBgIv.animate()
            .alpha(0f)
            .setDuration(DEFAULT_ALPHA_ANIM_DURATION)
            .start()
        binding.btnTitleTv.swapInBySize(animDuration = DEFAULT_SCALE_ANIM_DURATION)
    }

    fun swapOut() {
        binding.btnBgIv.animate()
            .alpha(1f)
            .setDuration(DEFAULT_ALPHA_ANIM_DURATION)
            .start()
        binding.btnTitleTv.swapOutBySize(
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
