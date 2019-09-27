package com.greylabsdev.pexwalls.presentation.screen.categoryimages

import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel


class CategoryImagesFragment : BaseFragment(
    layoutResId = R.layout.fragment_categoryimages,
    toolbarTitle = "Category",
    hasToolbarBackButton = true
) {
    override val viewModel: BaseViewModel? = null

    override fun initListeners() {
        super.initListeners()
//        back_iv.setOnClickListener { navigateBack() }
    }
}