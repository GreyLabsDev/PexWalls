package com.greylabsdev.pexwalls.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import java.io.Serializable
import androidx.navigation.findNavController

abstract class BaseActivity<VB: ViewBinding>(
    private val bindingFactory: (inflater: LayoutInflater) -> VB,
    @IdRes private val navigationHostId: Int? = null
) : AppCompatActivity() {

    protected lateinit var binding: VB
    protected abstract val viewModel: BaseViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        initViews()
        initListeners()
        initViewModelObserving()
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
        navigationHostId?.let { hostid ->
            navigationArgs?.let { args ->
                val bundle = Bundle()
                args.forEach { bundle.putSerializable(it.first, it.second) }
                this.findNavController(hostid).navigate(destinationId, bundle)
            } ?: run {
                this.findNavController(hostid).navigate(destinationId)
            }
        }
    }
}
