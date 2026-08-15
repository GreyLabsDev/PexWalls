package com.greylabsdev.pexwalls.presentation.screen.host

import android.content.Context
import android.content.Intent
import android.widget.LinearLayout.LayoutParams
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.databinding.ActivityHostBinding
import com.greylabsdev.pexwalls.presentation.base.BaseActivity
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.view.navigation.ActiveButtonsManager
import com.greylabsdev.pexwalls.presentation.ext.applySystemBarInsetsPadding
import com.greylabsdev.pexwalls.presentation.view.navigation.NavigationButton

class HostActivity : BaseActivity<ActivityHostBinding>(
    bindingFactory = ActivityHostBinding::inflate,
    navigationHostId = R.id.navigation_host_fr
) {
    override val viewModel: BaseViewModel? = null
    private val activeScreensManager = ActiveButtonsManager()
    private lateinit var navController: NavController
    private val mainDestinations = setOf(
        R.id.homeFragment,
        R.id.curatedPhotosFragment,
        R.id.searchFragment,
        R.id.favoritesFragment
    )
    private val destinationButtons = mutableMapOf<Int, NavigationButton>()

    override fun hideNavigation() {
        binding.navigationButtonsContainer.isVisible = false
    }

    override fun showNavigation() {
        if (::navController.isInitialized &&
            navController.currentDestination?.id in mainDestinations
        ) {
            binding.navigationButtonsContainer.isVisible = true
        }
    }

    override fun initViews() {
        binding.navigationButtonsContainer.applySystemBarInsetsPadding(applyBottom = true)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navigation_host_fr) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.navigationButtonsContainer.isVisible = destination.id in mainDestinations
            destinationButtons[destination.id]?.let { activeScreensManager.toggleButtonToActive(it) }
        }
        initNavigation()
    }

    private fun initNavigation() {
        setupNavButtons(
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_home),
                iconRes = R.drawable.ic_home,
                destinationId = R.id.homeFragment
            ),
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_curated),
                iconRes = R.drawable.ic_curated,
                destinationId = R.id.curatedPhotosFragment
            ),
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_search),
                iconRes = R.drawable.ic_search,
                destinationId = R.id.searchFragment
            ),
            NavigationButton(
                context = this,
                title = getString(R.string.navigation_favorite),
                iconRes = R.drawable.ic_favorite_fill,
                destinationId = R.id.favoritesFragment
            ),
        )
    }

    private fun setupNavButtons(vararg buttons: NavigationButton) {
        buttons.forEach { button ->
            button.layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                1.0f
            )
            binding.navigationButtonsContainer.addView(button)
            activeScreensManager.addButton(button)
            destinationButtons[button.destinationId] = button
            button.setOnClickListener {
                navigateToTab(button.destinationId)
                activeScreensManager.toggleButtonToActive(button)
            }
        }
        activeScreensManager.onActiveButtonChangedAction = { activeButton, inactiveButtons ->
            activeButton.swapOut()
            inactiveButtons.forEach { it.swapIn() }
        }
    }

    private fun navigateToTab(@IdRes destinationId: Int) {
        if (!::navController.isInitialized) return
        if (navController.currentDestination == null) return
        if (navController.currentDestination?.id == destinationId) return
        if (navController.currentDestination?.id == R.id.splashFragment) return

        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setRestoreState(true)
            .setPopUpTo(R.id.homeFragment, false, saveState = true)
            .build()

        navController.navigate(destinationId, null, navOptions)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HostActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}
