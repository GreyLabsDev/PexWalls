package com.greylabsdev.pexwalls.presentation.view

import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.ext.addEndListener
import kotlinx.android.synthetic.main.view_progress.view.*

class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val animator: ViewAnimator

    init {
        inflate(context, R.layout.view_progress, this)
        animator = ViewAnimator()
        startLoadingAnimation()
    }

    private fun startLoadingAnimation() {
        val scaleFirst = AnimatorSet().apply {
            play(animator.shiftUp(p_letter_tv, 450))
                .with(animator.shiftDown(w_letter_tv, 450))
                .with(animator.fadeIn(divider_v, 450))
        }
        val scaleBack = AnimatorSet().apply {
            play(animator.shiftBack(w_letter_tv, 450))
                .with(animator.shiftBack(p_letter_tv, 450))
                .with(animator.fadeOut(divider_v, 450))
        }
        val scaleBackSecond = AnimatorSet().apply {
            play(animator.shiftBack(p_letter_tv, 450))
                .with(animator.shiftBack(w_letter_tv, 450))
                .with(animator.fadeOut(divider_v, 450))
        }
        val scaleSecond = AnimatorSet().apply {
            play(animator.shiftUp(w_letter_tv, 450))
                .with(animator.shiftDown(p_letter_tv, 450))
                .with(animator.fadeIn(divider_v, 450))
        }
        val scaleFull = AnimatorSet().apply {
            play(scaleFirst).before(scaleBack)
            play(scaleSecond).after(scaleBack)
        }
        val loadingAnimatorSet = AnimatorSet().apply {
            play(scaleFull).before(scaleBackSecond)
            addEndListener { this.start() }
        }
        loadingAnimatorSet.start()
    }

    fun show() {
        animator.show(this)
    }

    fun hide() {
        animator.hide(this)
    }
}
