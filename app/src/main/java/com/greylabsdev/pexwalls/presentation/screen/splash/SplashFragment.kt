package com.greylabsdev.pexwalls.presentation.screen.splash

import android.os.Handler
import android.view.View
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.databinding.FragmentSplashBinding
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView

class SplashFragment() : BaseFragment<FragmentSplashBinding>(
    bindingFactory = FragmentSplashBinding::inflate,
    hideNavigation = true
) {
    override val viewModel: BaseViewModel? = null
    override val toolbarTitle: String? = null
    override val contentView: View? = null
    override val placeholderView: PlaceholderView? = null

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({ navigateTo(R.id.homeFragment) }, 2000)
    }
}
