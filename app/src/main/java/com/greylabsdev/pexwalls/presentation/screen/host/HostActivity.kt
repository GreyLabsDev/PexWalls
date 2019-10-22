package com.greylabsdev.pexwalls.presentation.screen.host

import android.content.Context
import android.content.Intent
import androidx.core.view.isVisible
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseActivity
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.view.navigation.NavigationButton
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.android.synthetic.main.view_navigation.*
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

    override fun initViews() {
        navigation_view.setupButtons(
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_home),
                iconRes = R.drawable.ic_home,
                onClickAction = { navigateTo(R.id.homeFragment) }
            ),
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_curated),
                iconRes = R.drawable.ic_curated,
                onClickAction = { navigateTo(R.id.curatedPhotosFragment) }
            ),
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_search),
                iconRes = R.drawable.ic_search,
                onClickAction = { navigateTo(R.id.searchFragment) }
            ),
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_favorite),
                iconRes = R.drawable.ic_favorite_fill,
                onClickAction = { navigateTo(R.id.favoritesFragment) }
            )
        )
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HostActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}