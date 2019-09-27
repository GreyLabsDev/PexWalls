package com.greylabsdev.pexwalls.presentation.screen.home

import android.graphics.Color
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.ext.setNavigationClickListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(
    layoutResId = R.layout.fragment_home,
    toolbarTitle = "Home",
    hasToolbarBackButton = false
) {
    override val viewModel: BaseViewModel? = null

    override fun initViews() {
        super.initViews()
        activity?.window?.statusBarColor = Color.WHITE
    }

    override fun initListeners() {
        super.initListeners()
        category_btn_tv.setNavigationClickListener(R.id.categoryImagesFragment)
    }
}