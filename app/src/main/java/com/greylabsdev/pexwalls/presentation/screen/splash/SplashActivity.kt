package com.greylabsdev.pexwalls.presentation.screen.splash

import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity(
    layoutResId = R.layout.activity_main
) {
    override val viewModel by viewModel<SplashViewModel>()

    override fun initViewModelObserving() {}

    override fun initListeners() {
        test_tv.setOnClickListener { viewModel.test() }
    }
}
