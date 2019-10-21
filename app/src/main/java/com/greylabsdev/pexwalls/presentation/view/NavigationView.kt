package com.greylabsdev.pexwalls.presentation.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Property
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout
import androidx.annotation.Px
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.ext.measureWidth
import com.greylabsdev.pexwalls.presentation.ext.pixToDp
import kotlinx.android.synthetic.main.view_navigation.view.*
import kotlin.math.roundToInt

class NavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    val activeScreensManager = ActiveScreensManager()

    val oneW: Int by lazy { this.home_title_tv.measureWidth() }
    val twoW: Int by lazy { this.curated_title_tv.measureWidth() }
    val threeW: Int by lazy { this.search_title_tv.measureWidth() }
    val fourW: Int by lazy { this.favorites_title_tv.measureWidth() }

    init {
        View.inflate(context, R.layout.view_navigation, this)

    }

    fun initListeners() {

        this.to_home_btn_ll.setOnClickListener {
            this.home_title_tv.swapOutBySize(swapWidth = oneW)
            this.curated_title_tv.swapInBySize()
            this.search_title_tv.swapInBySize()
            this.favorites_title_tv.swapInBySize()
        }
        this.to_curated_btn_ll.setOnClickListener {
            this.home_title_tv.swapInBySize()
            this.curated_title_tv.swapOutBySize(swapWidth = twoW)
            this.search_title_tv.swapInBySize()
            this.favorites_title_tv.swapInBySize()
        }
        this.to_search_btn_ll.setOnClickListener {
            this.home_title_tv.swapInBySize()
            this.curated_title_tv.swapInBySize()
            this.search_title_tv.swapOutBySize(swapWidth = threeW)
            this.favorites_title_tv.swapInBySize()
        }
        this.to_favorites_btn_ll.setOnClickListener {
            this.home_title_tv.swapInBySize()
            this.curated_title_tv.swapInBySize()
            this.search_title_tv.swapInBySize()
            this.favorites_title_tv.swapOutBySize(swapWidth = fourW)
        }
    }

    fun toggleActiveScreen(screenId: Int) {

    }

    class ActiveScreensManager() {
        private var _activeScreenId = 0
        val activeScreenId: Int
            get() = _activeScreenId

        val totalScreensCount = 4
        private val screens = listOf(0,1,2,3)

        val inactiveScreens: List<Int>
            get() = screens.filter { it != activeScreenId }

        fun toggleScreenToActive(screenId: Int) {
            _activeScreenId = screenId
        }
    }

    fun View.swapIn(onEndAction:(() -> Unit)? = null) {
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
        this.startAnimation(anim)
    }

    fun View.swapOut(onEndAction:(() -> Unit)? = null) {
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
        this.startAnimation(anim)
    }

    fun View.swapInBySize(animDuration: Long = 350) {
//        val size = this.context.pixToDp(this.height)
//        val animator = this.getSizeAnimator(
//            size, 0
//        ).apply {
//            duration = animDuration
//        }
//        animator.start()
        val animator = ValueAnimator.ofInt(this.measuredWidth, 0)
        animator.addUpdateListener {
            val value = animator.animatedValue as Int
            val layoutParams = this.layoutParams as ViewGroup.LayoutParams
            layoutParams.width = value
            this.layoutParams = layoutParams
        }
        animator.duration = animDuration
        animator.start()
    }

    fun View.swapOutBySize(animDuration: Long = 350, swapWidth: Int = 100) {
//        val size = this.context.pixToDp(this.height)
//        val animator = this.getSizeAnimator(
//            0, size
//        ).apply {
//            duration = animDuration
//        }
//        animator.start()
        val animator = ValueAnimator.ofInt(0, swapWidth)
        animator.addUpdateListener {
            val value = animator.animatedValue as Int
            val layoutParams = this.layoutParams as ViewGroup.LayoutParams
            layoutParams.width = value
            this.layoutParams = layoutParams
        }
        animator.duration = animDuration
        animator.start()

    }

    private fun View.getSizeAnimator(@Px fromSize: Int, @Px toSize: Int): ObjectAnimator {
        return ObjectAnimator.ofInt(
            this,
            ViewSizeProperty(),
            context.dpToPix(fromSize).roundToInt(),
            context.dpToPix(toSize).roundToInt()
        )
    }

    private class ViewSizeProperty : Property<View, Int>(Int::class.java, "") {

        override fun get(view: View): Int {
            return view.height
        }

        override fun set(view: View, value: Int) {
            view.layoutParams.height = value
            view.layoutParams.width = value
            view.layoutParams = view.layoutParams
        }
    }
}