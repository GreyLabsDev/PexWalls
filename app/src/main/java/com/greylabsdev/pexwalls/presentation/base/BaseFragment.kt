package com.greylabsdev.pexwalls.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(
    @LayoutRes private val layoutResId: Int
): Fragment() {

    protected abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
        initViewModelObserving()
    }

    protected open fun initViews() {}
    protected open fun initListeners() {}
    protected abstract fun initViewModelObserving()

    protected open fun showLoading(isShow: Boolean) {}
    protected open fun showError(errorMessage: String) {}
    protected open fun showPlaceholder(isShow: Boolean, titleText: String? = null) {}
}