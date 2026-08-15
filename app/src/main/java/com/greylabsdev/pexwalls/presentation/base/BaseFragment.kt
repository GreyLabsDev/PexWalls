package com.greylabsdev.pexwalls.presentation.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.greylabsdev.pexwalls.databinding.LayoutToolbarBinding
import com.greylabsdev.pexwalls.presentation.ext.applySystemBarInsetsPadding
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView
import java.io.Serializable

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val hasToolbarBackButton: Boolean = false,
    private val transparentStatusBar: Boolean = false,
    private val hideNavigation: Boolean = false
) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB?
        get() = _binding

    protected abstract val viewModel: BaseViewModel?
    protected abstract val toolbarTitle: String?
    protected abstract val placeholderView: PlaceholderView?
    protected abstract val contentView: View?
    protected var toolbarView: LayoutToolbarBinding? = null

    private var onPermissionGrantedAction: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupSystemBars()
        _binding = bindingFactory(layoutInflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onStart() {
        super.onStart()

        initViews()
        initListeners()
        initToolbar()
        initViewModelObserving()
        doInitialCalls()
    }

    protected open fun initViews() {}
    protected open fun initListeners() {}
    protected open fun initViewModelObserving() {
        viewModel?.progressState?.observe(this, Observer { progressState ->
            when (progressState) {
                is ProgressState.DONE -> {
                    placeholderView?.setState(PlaceholderView.PlaceholderState.GONE)
                    contentView?.isVisible = true
                }

                is ProgressState.LOADING -> {
                    placeholderView?.setState(PlaceholderView.PlaceholderState.LOADING)
                    contentView?.isVisible = false
                }

                is ProgressState.ERROR -> {
                    placeholderView?.setState(PlaceholderView.PlaceholderState.ERROR)
                    contentView?.isVisible = false
                }

                is ProgressState.INITIAL -> {
                    placeholderView?.setState(PlaceholderView.PlaceholderState.INITIAL)
                    contentView?.isVisible = false
                }

                is ProgressState.EMPTY -> {
                    placeholderView?.setState(PlaceholderView.PlaceholderState.EMPTY)
                    contentView?.isVisible = false
                }
            }
        })
    }

    protected open fun doInitialCalls() {}

    protected fun navigateBack() {
        requireView().findNavController().popBackStack()
    }

    protected fun navigateTo(
        @IdRes destinationId: Int,
        navigationArgs: List<Pair<String, Serializable>>? = null
    ) {
        navigationArgs?.let { args ->
            val bundle = Bundle()
            args.forEach { bundle.putSerializable(it.first, it.second) }
            requireView().findNavController().navigate(destinationId, bundle)
        } ?: run {
            requireView().findNavController().navigate(destinationId)
        }
    }

    protected fun requestStoragePermissionWithAction(permissionNeededAction: () -> Unit) {
        onPermissionGrantedAction = permissionNeededAction
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE &&
            grantResults.first() == PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionGrantedAction?.invoke()
            onPermissionGrantedAction = null
        }
    }

    private fun initToolbar() {
        toolbarView?.let {
            it.toolbarContainer.applySystemBarInsetsPadding(applyTop = true)
            it.toolbarTitleTv.text = toolbarTitle ?: ""
            it.backIv.isVisible = hasToolbarBackButton
            if (hasToolbarBackButton) {
                it.backIv.setOnClickListener { navigateBack() }
            }
        }
    }

    private fun setupSystemBars() {
        val window = activity?.window ?: return
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)

        if (hideNavigation) {
            (requireActivity() as BaseActivity<*>).hideNavigation()
        } else if (hideNavigation.not() && transparentStatusBar.not()) {
            (requireActivity() as BaseActivity<*>).showNavigation()
        }

        if (transparentStatusBar) {
            (requireActivity() as BaseActivity<*>).hideNavigation()
            insetsController.isAppearanceLightStatusBars = false
            insetsController.isAppearanceLightNavigationBars = false
        } else {
            insetsController.isAppearanceLightStatusBars = true
            insetsController.isAppearanceLightNavigationBars = true
        }
    }

    companion object {
        const val PERMISSION_CODE = 223
    }
}
