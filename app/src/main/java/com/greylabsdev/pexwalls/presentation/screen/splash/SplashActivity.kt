package com.greylabsdev.pexwalls.presentation.screen.splash

import android.os.Build
import android.os.Bundle
import android.os.Handler
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseActivity
import com.greylabsdev.pexwalls.presentation.screen.host.HostActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity(
    layoutResId = R.layout.activity_splash
) {
    override val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = getColor(R.color.colorBackground)
        }
        super.onCreate(savedInstanceState)
        Handler().postDelayed({HostActivity.start(this)}, 1500)
    }
}
