package com.greylabsdev.pexwalls.presentation.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.*
import androidx.core.view.isVisible
import com.greylabsdev.pexwalls.presentation.ext.addOnEndAction
import com.greylabsdev.pexwalls.presentation.ext.dpToPix

class ViewAnimator {

    fun show(viewToAnimate: View) {
        viewToAnimate.isVisible = true
        swapOut(viewToAnimate)
    }

    fun hide(viewToAnimate: View) {
        swapIn(viewToAnimate) {viewToAnimate.isVisible = false}
    }

    private fun swapOut(v: View, onEndAction:(() -> Unit)? = null) {
        val anim = ScaleAnimation(
            0f, 1f,
            0f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            fillAfter = true
            duration = 200L
            startOffset = 0L
            interpolator = AccelerateDecelerateInterpolator()
            setAnimationListener(
                object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {}
                    override fun onAnimationEnd(p0: Animation?) {onEndAction?.invoke()}
                    override fun onAnimationStart(p0: Animation?) {}
                }
            )
        }
        v.startAnimation(anim)
    }

    private fun swapIn(v: View, onEndAction:(() -> Unit)? = null) {
        val anim = ScaleAnimation(
            1f, 0f,
            1f, 0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            fillAfter = true
            duration = 200L
            startOffset = 0L
            interpolator = AccelerateDecelerateInterpolator()
            setAnimationListener(
                object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {}
                    override fun onAnimationEnd(p0: Animation?) {onEndAction?.invoke()}
                    override fun onAnimationStart(p0: Animation?) {}
                }
            )
        }
        v.startAnimation(anim)
    }


    fun shiftUp(v: View, animDuration: Long = 100L, onEndAction:(() -> Unit)? = null): ObjectAnimator {
        val moveHeight = v.height.toFloat()
        return ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, -100f).apply {
            duration = animDuration
            interpolator = DecelerateInterpolator()
            onEndAction?.let { addOnEndAction(it) }
        }
    }

    fun shiftBack(v: View, animDuration: Long = 100L, onEndAction:(() -> Unit)? = null): ObjectAnimator {
        val moveHeight = v.height.toFloat()
        return ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, 0f).apply {
            duration = animDuration
            interpolator = DecelerateInterpolator()
            onEndAction?.let { addOnEndAction(it) }
        }
    }

    fun shiftDown(v: View, animDuration: Long = 100L, onEndAction:(() -> Unit)? = null): ObjectAnimator {
        val moveHeight = v.height.toFloat()
        return ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, 100f).apply {
            duration = animDuration
            interpolator = DecelerateInterpolator()
            onEndAction?.let { addOnEndAction(it) }
        }
    }

    fun fadeOut(v: View, animDuration: Long = 100L, onEndAction:(() -> Unit)? = null): ObjectAnimator {
        return ObjectAnimator.ofFloat(v, View.ALPHA, 1f, 0f).apply {
            duration = animDuration
            onEndAction?.let { addOnEndAction(it) }
        }
    }

    fun fadeIn(v: View, animDuration: Long = 100L, onEndAction:(() -> Unit)? = null): ObjectAnimator {
        return ObjectAnimator.ofFloat(v, View.ALPHA, 0f, 1f).apply {
            duration = animDuration
            onEndAction?.let { addOnEndAction(it) }
        }
    }
}