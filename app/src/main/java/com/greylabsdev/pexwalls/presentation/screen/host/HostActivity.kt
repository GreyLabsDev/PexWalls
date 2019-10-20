package com.greylabsdev.pexwalls.presentation.screen.host

import android.content.Context
import android.content.Intent
import androidx.core.view.isVisible
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseActivity
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.android.synthetic.main.view_navigation.view.*

class HostActivity : BaseActivity(
    layoutResId = R.layout.activity_host,
    navigationHostId = R.id.navigation_host_fr
) {
    override val viewModel: BaseViewModel? = null

    override fun hideNavigation() {
        navigation_view.isVisible = false
    }

    override fun showNavigation() {
        navigation_view.isVisible = true
    }

    override fun initListeners() {
        navigation_view.to_home_btn.setOnClickListener { navigateTo(R.id.homeFragment) }
        navigation_view.to_curated_btn.setOnClickListener { navigateTo(R.id.curatedPhotosFragment) }
        navigation_view.to_search_btn.setOnClickListener { navigateTo(R.id.searchFragment) }
        navigation_view.to_favorites_btn.setOnClickListener { navigateTo(R.id.favoritesFragment) }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HostActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}