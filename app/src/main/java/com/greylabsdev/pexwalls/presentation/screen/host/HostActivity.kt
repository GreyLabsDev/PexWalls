package com.greylabsdev.pexwalls.presentation.screen.host

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseActivity
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : BaseActivity(
    layoutResId = R.layout.activity_host
) {
    override val viewModel: BaseViewModel? = null

    override fun hideNavigation() {
        navigation_container_ll.isVisible = false
    }

    override fun showNavigation() {
        navigation_container_ll.isVisible = true
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HostActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}