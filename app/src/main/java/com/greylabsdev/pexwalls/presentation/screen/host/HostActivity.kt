package com.greylabsdev.pexwalls.presentation.screen.host

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseActivity
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.ext.then

class HostActivity : BaseActivity(
    layoutResId = R.layout.activity_host
) {
    override val viewModel: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O).then {
            window.navigationBarColor = getColor(R.color.colorBackground)
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HostActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}