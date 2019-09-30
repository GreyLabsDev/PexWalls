package com.greylabsdev.pexwalls.presentation.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.appbar.AppBarLayout
import com.greylabsdev.pexwalls.presentation.ext.setNavigationClickListener
import com.greylabsdev.pexwalls.presentation.ext.then
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import java.io.Serializable

abstract class BaseFragment(
    @LayoutRes private val layoutResId: Int,
    private val hasToolbarBackButton: Boolean = false
): Fragment() {

    protected abstract val viewModel: BaseViewModel?
    protected abstract val toolbarTitle: String?
    protected abstract val progressBar: View?

    private val toolbarView: AppBarLayout? by lazy { toolbar_container }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onStart() {
        super.onStart()

        activity?.window?.statusBarColor = Color.WHITE
        initViews()
        initListeners()
        initToolbar()
        initViewModelObserving()
        doInitialCalls()
    }

    protected open fun initViews() {}
    protected open fun initListeners() {}
    protected open fun initViewModelObserving() {
        viewModel?.progressState?.observe(this, Observer {progressState ->
            when (progressState) {
                is ProgressState.DONE -> {
                    progressBar?.isVisible = false
                }
                is ProgressState.LOADING -> {
                    progressBar?.isVisible = true
                }
                is ProgressState.ERROR -> {
                    progressBar?.isVisible = false
                }
            }
        })
    }
    protected open fun doInitialCalls() {}

    protected open fun showLoading(isShow: Boolean) {}
    protected open fun showError(errorMessage: String) {}
    protected open fun showPlaceholder(isShow: Boolean, titleText: String? = null) {}

    protected fun navigateBack() {
        Navigation.findNavController(requireView()).popBackStack()
    }

    protected fun navigateTo(
        @IdRes destinationId: Int,
        navigationArgs: List<Pair<String, Serializable>>? = null
    ) {
        navigationArgs?.let {args ->
            val bundle = Bundle()
            args.forEach { bundle.putSerializable(it.first, it.second) }
            Navigation.findNavController(requireView()).navigate(destinationId, bundle)
        } ?: run {
            Navigation.findNavController(requireView()).navigate(destinationId)
        }
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