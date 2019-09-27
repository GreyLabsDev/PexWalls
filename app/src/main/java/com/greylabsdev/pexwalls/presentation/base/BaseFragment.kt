package com.greylabsdev.pexwalls.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.appbar.AppBarLayout
import com.greylabsdev.pexwalls.presentation.ext.then
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

abstract class BaseFragment(
    @LayoutRes private val layoutResId: Int,
    private val toolbarTitle: String? = null,
    private val hasToolbarBackButton: Boolean = false
): Fragment() {

    protected abstract val viewModel: BaseViewModel?
    private val toolbarView: AppBarLayout? by lazy { toolbar_container }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        initViews()
        initListeners()
        initToolbar()
        initViewModelObserving()
    }

    protected open fun initViews() {}
    protected open fun initListeners() {}
    protected open fun initViewModelObserving() {}

    protected open fun showLoading(isShow: Boolean) {}
    protected open fun showError(errorMessage: String) {}
    protected open fun showPlaceholder(isShow: Boolean, titleText: String? = null) {}

    protected fun navigateBack() {
        Navigation.findNavController(requireView()).popBackStack()
    }

    private fun initToolbar() {
        toolbarView?.let {
            toolbar_container.toolbar_title_tv.text = toolbarTitle ?: ""
            toolbar_container.back_iv.isVisible = hasToolbarBackButton
            hasToolbarBackButton.then {
                toolbar_container.setOnClickListener { navigateBack() }
            }
        }
    }
}