package com.greylabsdev.pexwalls.presentation.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import java.io.Serializable

abstract class BaseActivity<VB: ViewBinding>(
    private val bindingFactory: (inflater: LayoutInflater) -> VB,
    @IdRes private val navigationHostId: Int? = null
) : AppCompatActivity() {

    protected lateinit var binding: VB
    protected abstract val viewModel: BaseViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupEdgeToEdge()
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        initViews()
        initListeners()
        initViewModelObserving()
    }

    private fun setupEdgeToEdge() {
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
    }

    open fun hideNavigation() {}
    open fun showNavigation() {}

    protected open fun initViews() {}
    protected open fun initListeners() {}
    protected open fun initViewModelObserving() {}

    protected fun navigateTo(
        @IdRes destinationId: Int,
        navigationArgs: List<Pair<String, Serializable>>? = null
    ) {
        val navController = navigationHostId
            ?.let { hostId ->
                (supportFragmentManager.findFragmentById(hostId) as? NavHostFragment)?.navController
            } ?: return

        if (navController.currentDestination == null) return

        navigationArgs?.let { args ->
            val bundle = Bundle()
            args.forEach { bundle.putSerializable(it.first, it.second) }
            navController.navigate(destinationId, bundle)
        } ?: run {
            navController.navigate(destinationId)
        }
    }
}
