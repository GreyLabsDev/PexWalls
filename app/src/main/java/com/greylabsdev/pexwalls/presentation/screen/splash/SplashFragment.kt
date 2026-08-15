package com.greylabsdev.pexwalls.presentation.screen.splash

import android.os.Handler
import android.os.Looper
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

    private val navigationHandler = Handler(Looper.getMainLooper())
    private val navigateToHome = Runnable {
        navigateTo(R.id.action_splashFragment_to_homeFragment)
    }

    override fun onStart() {
        super.onStart()
        navigationHandler.postDelayed(navigateToHome, SPLASH_DELAY_MS)
    }

    override fun onStop() {
        navigationHandler.removeCallbacks(navigateToHome)
        super.onStop()
    }

    companion object {
        private const val SPLASH_DELAY_MS = 2000L
    }
}
