package com.greylabsdev.pexwalls.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import com.greylabsdev.pexwalls.R
import kotlinx.android.synthetic.main.view_placeholder.view.*

class PlaceholderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    var onTryNowBtnClickAction: (() -> Unit)? = null
        set(value) {
            try_now_btn.isVisible = value != null
            value?.let { action ->
                try_now_btn.setOnClickListener { action.invoke() }
            }
            field = value
        }

    init {
        inflate(context, R.layout.view_placeholder, this)
        try_now_btn.setOnClickListener { hideAll() }
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
        loading_container_ll.isVisible = true
        error_container_ll.isVisible = false
        initial_container_ll.isVisible = false
        empty_container_ll.isVisible = false
    }
    private fun showError() {
        show()
        loading_container_ll.isVisible = false
        error_container_ll.isVisible = true
        initial_container_ll.isVisible = false
        empty_container_ll.isVisible = false
    }
    private fun showEmpty() {
        show()
        loading_container_ll.isVisible = false
        error_container_ll.isVisible = false
        initial_container_ll.isVisible = false
        empty_container_ll.isVisible = true
    }
    private fun showInitial() {
        show()
        loading_container_ll.isVisible = false
        error_container_ll.isVisible = false
        initial_container_ll.isVisible = true
        empty_container_ll.isVisible = false
    }
    private fun hideAll() {
        loading_container_ll.isVisible = false
        error_container_ll.isVisible = false
        initial_container_ll.isVisible = false
        empty_container_ll.isVisible = false
        this.isVisible = false
    }
    private fun show() {
        this.isVisible = true
    }

    enum class PlaceholderState {
        LOADING, ERROR, INITIAL, EMPTY, GONE
    }
}