package com.greylabsdev.pexwalls.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(
    @LayoutRes private val layoutResId: Int
): AppCompatActivity() {

    protected abstract val viewModel: BaseViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        initViews()
        initListeners()
        initViewModelObserving()
    }

    protected open fun initViews() {}
    protected open fun initListeners() {}
    protected open fun initViewModelObserving() {}

    protected open fun showLoading(isShow: Boolean) {}
    protected open fun showError(errorMessage: String) {}
    protected open fun showPlaceholder(isShow: Boolean, titleText: String? = null) {}
}