package com.greylabsdev.pexwalls.presentation.screen.host

import android.content.Context
import android.content.Intent
import android.widget.LinearLayout.LayoutParams
import androidx.core.view.isVisible
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.databinding.ActivityHostBinding
import com.greylabsdev.pexwalls.presentation.base.BaseActivity
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.view.navigation.ActiveButtonsManager
import com.greylabsdev.pexwalls.presentation.view.navigation.NavigationButton

class HostActivity : BaseActivity<ActivityHostBinding>(
    bindingFactory = ActivityHostBinding::inflate, navigationHostId = R.id.navigation_host_fr
) {
    override val viewModel: BaseViewModel? = null
    private val activeScreensManager = ActiveButtonsManager()

    override fun hideNavigation() {
        binding.navigationButtonsContainer.isVisible = false
    }

    override fun showNavigation() {
        binding.navigationButtonsContainer.isVisible = true
    }

    override fun initViews() {
        initNavigation()
    }

    private fun initNavigation() {
        setupNavButtons(
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_home),
                iconRes = R.drawable.ic_home,
                onClickAction = { navigateTo(R.id.homeFragment) }),
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_curated),
                iconRes = R.drawable.ic_curated,
                onClickAction = { navigateTo(R.id.curatedPhotosFragment) }),
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_search),
                iconRes = R.drawable.ic_search,
                onClickAction = { navigateTo(R.id.searchFragment) }),
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_favorite),
                iconRes = R.drawable.ic_favorite_fill,
                onClickAction = { navigateTo(R.id.favoritesFragment) }),
            )
    }

    private fun setupNavButtons(vararg buttons: NavigationButton) {
        buttons.forEach { button ->
            button.layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f
            )
            binding.navigationButtonsContainer.addView(button)
            activeScreensManager.addButton(button)
            button.setOnClickListener {
                activeScreensManager.toggleButtonToActive(button)
                button.onClickAction?.invoke()
            }
        }
        activeScreensManager.onActiveButtonChangedAction = { activeButton, inactiveButtons ->
            activeButton.swapOut()
            inactiveButtons.forEach { it.swapIn() }
        }
        activeScreensManager.toggleButtonToActive(buttons.first())
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HostActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}
