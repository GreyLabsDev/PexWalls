package com.greylabsdev.pexwalls.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import com.greylabsdev.pexwalls.R

class PlaceholderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    var onTryNowBtnClickAction: (() -> Unit)? = null
        set(value) {
            tryNowBtn.isVisible = value != null
            value?.let { action ->
                tryNowBtn.setOnClickListener { action.invoke() }
            }
            field = value
        }

    private val loadingContainer: LinearLayout
    private val errorContainer: LinearLayout
    private val initialContainer: LinearLayout
    private val emptyContainer: LinearLayout
    private val tryNowBtn: MaterialButton

    init {
        inflate(context, R.layout.view_placeholder, this)
        loadingContainer = findViewById(R.id.loading_container_ll)
        errorContainer = findViewById(R.id.error_container_ll)
        initialContainer = findViewById(R.id.initial_container_ll)
        emptyContainer = findViewById(R.id.empty_container_ll)
        tryNowBtn = findViewById(R.id.try_now_btn)

        tryNowBtn.setOnClickListener { hideAll() }
    }

    fun setState(state: PlaceholderState) {
        when (state) {
            PlaceholderState.LOADING -> showLoading()
            PlaceholderState.ERROR -> showError()
            PlaceholderState.INITIAL -> showInitial()
            PlaceholderState.EMPTY -> showEmpty()
            PlaceholderState.GONE -> hideAll()
        }
    }

    private fun showLoading() {
        show()
        loadingContainer.isVisible = true
        errorContainer.isVisible = false
        initialContainer.isVisible = false
        emptyContainer.isVisible = false
    }
    private fun showError() {
        show()
        loadingContainer.isVisible = false
        errorContainer.isVisible = true
        initialContainer.isVisible = false
        emptyContainer.isVisible = false
    }
    private fun showEmpty() {
        show()
        loadingContainer.isVisible = false
        errorContainer.isVisible = false
        initialContainer.isVisible = false
        emptyContainer.isVisible = true
    }
    private fun showInitial() {
        show()
        loadingContainer.isVisible = false
        errorContainer.isVisible = false
        initialContainer.isVisible = true
        emptyContainer.isVisible = false
    }
    private fun hideAll() {
        loadingContainer.isVisible = false
        errorContainer.isVisible = false
        initialContainer.isVisible = false
        emptyContainer.isVisible = false
        this.isVisible = false
    }
    private fun show() {
        this.isVisible = true
    }

    enum class PlaceholderState {
        LOADING, ERROR, INITIAL, EMPTY, GONE
    }
}