package com.greylabsdev.pexwalls.presentation.view

import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.ext.addEndListener

class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private val animator: ViewAnimator
    private val pLetter: TextView
    private val wLetter: TextView
    private val divider: View

    init {
        inflate(context, R.layout.view_progress, this)
        animator = ViewAnimator()
        pLetter = findViewById(R.id.p_letter_tv)
        wLetter = findViewById(R.id.w_letter_tv)
        divider = findViewById(R.id.divider_v)

        startLoadingAnimation()
    }

    private fun startLoadingAnimation() {
        val scaleFirst = AnimatorSet().apply {
            play(animator.shiftUp(pLetter, 450))
                .with(animator.shiftDown(wLetter, 450))
                .with(animator.fadeIn(divider, 450))


        }
        val scaleBack = AnimatorSet().apply {
            play(animator.shiftBack(wLetter, 450))
                .with(animator.shiftBack(pLetter, 450))
                .with(animator.fadeOut(divider, 450))

        }
        val scaleBackSecond = AnimatorSet().apply {
            play(animator.shiftBack(pLetter, 450))
                .with(animator.shiftBack(wLetter, 450))
                .with(animator.fadeOut(divider, 450))

        }
        val scaleSecond = AnimatorSet().apply {
            play(animator.shiftUp(wLetter, 450))
                .with(animator.shiftDown(pLetter, 450))
                .with(animator.fadeIn(divider, 450))
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