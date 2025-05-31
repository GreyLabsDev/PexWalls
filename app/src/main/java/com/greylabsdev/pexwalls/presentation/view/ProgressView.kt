package com.greylabsdev.pexwalls.presentation.view

import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.databinding.ViewProgressBinding
import com.greylabsdev.pexwalls.presentation.ext.addEndListener

class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val animator: ViewAnimator
    private val binding: ViewProgressBinding

    init {
        inflate(context, R.layout.view_progress, this)
        binding = ViewProgressBinding.bind(this)
        animator = ViewAnimator()
        startLoadingAnimation()
    }

    private fun startLoadingAnimation() {
        val scaleFirst = AnimatorSet().apply {
            play(animator.shiftUp(binding.pLetterTv, 450))
                .with(animator.shiftDown(binding.wLetterTv, 450))
                .with(animator.fadeIn(binding.dividerV, 450))
        }
        val scaleBack = AnimatorSet().apply {
            play(animator.shiftBack(binding.pLetterTv, 450))
                .with(animator.shiftBack(binding.wLetterTv, 450))
                .with(animator.fadeOut(binding.dividerV, 450))
        }
        val scaleBackSecond = AnimatorSet().apply {
            play(animator.shiftBack(binding.pLetterTv, 450))
                .with(animator.shiftBack(binding.wLetterTv, 450))
                .with(animator.fadeOut(binding.dividerV, 450))
        }
        val scaleSecond = AnimatorSet().apply {
            play(animator.shiftUp(binding.pLetterTv, 450))
                .with(animator.shiftDown(binding.wLetterTv, 450))
                .with(animator.fadeIn(binding.dividerV, 450))
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
