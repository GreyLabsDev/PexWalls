package com.greylabsdev.pexwalls.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.databinding.ViewPlaceholderBinding

class PlaceholderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewPlaceholderBinding

    var onTryNowBtnClickAction: (() -> Unit)? = null
        set(value) {
            binding.tryNowBtn.isVisible = value != null
            value?.let { action ->
                binding.tryNowBtn.setOnClickListener { action.invoke() }
            }
            field = value
        }

    init {
        inflate(context, R.layout.view_placeholder, this)
        binding = ViewPlaceholderBinding.bind(this)
        binding.tryNowBtn.setOnClickListener { hideAll() }
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
        binding.loadingContainerLl.isVisible = true
        binding.errorContainerLl.isVisible = false
        binding.initialContainerLl.isVisible = false
        binding.emptyContainerLl.isVisible = false
    }

    private fun showError() {
        show()
        binding.loadingContainerLl.isVisible = false
        binding.errorContainerLl.isVisible = true
        binding.initialContainerLl.isVisible = false
        binding.emptyContainerLl.isVisible = false
    }

    private fun showEmpty() {
        show()
        binding.loadingContainerLl.isVisible = false
        binding.errorContainerLl.isVisible = false
        binding.initialContainerLl.isVisible = false
        binding.emptyContainerLl.isVisible = true
    }

    private fun showInitial() {
        show()
        binding.loadingContainerLl.isVisible = false
        binding.errorContainerLl.isVisible = false
        binding.initialContainerLl.isVisible = true
        binding.emptyContainerLl.isVisible = false
    }

    private fun hideAll() {
        binding.loadingContainerLl.isVisible = false
        binding.errorContainerLl.isVisible = false
        binding.initialContainerLl.isVisible = false
        binding.emptyContainerLl.isVisible = false
        this.isVisible = false
    }

    private fun show() {
        this.isVisible = true
    }

    enum class PlaceholderState {
        LOADING, ERROR, INITIAL, EMPTY, GONE
    }
}
