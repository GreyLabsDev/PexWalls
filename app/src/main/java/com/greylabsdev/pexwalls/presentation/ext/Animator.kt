package com.greylabsdev.pexwalls.presentation.ext

import android.animation.Animator
import android.animation.ValueAnimator

fun ValueAnimator.addOnEndAction(action: () -> Unit) {
    this.addListener(object : Animator.AnimatorListener {
        override fun onAnimationEnd(p0: Animator?) { action.invoke() }
        override fun onAnimationRepeat(p0: Animator?) {}
        override fun onAnimationCancel(p0: Animator?) {}
        override fun onAnimationStart(p0: Animator?) {}
    })
}

inline fun Animator.addEndListener(
    crossinline onEnd: (animator: Animator) -> Unit = {}
): Animator.AnimatorListener {
    val listener = object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator) {}
        override fun onAnimationEnd(p0: Animator) = onEnd(p0)
        override fun onAnimationCancel(p0: Animator) {}
        override fun onAnimationStart(p0: Animator) {}
    }
    addListener(listener)
    return listener
}
