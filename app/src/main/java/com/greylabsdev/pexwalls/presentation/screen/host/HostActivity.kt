package com.greylabsdev.pexwalls.presentation.screen.host

import android.content.Context
import android.content.Intent
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseActivity
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel

class HostActivity : BaseActivity(
    layoutResId = R.layout.activity_host
) {
    override val viewModel: BaseViewModel? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HostActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}