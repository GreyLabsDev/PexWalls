package com.greylabsdev.pexwalls.presentation.screen.home

import android.graphics.Color
import android.view.View
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.setNavigationClickListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(
    layoutResId = R.layout.fragment_home,
    hasToolbarBackButton = false
) {
    override val viewModel: BaseViewModel? = null
    override val toolbarTitle = "Home"
    override val progressBar: View? = null

    override fun initListeners() {
        category_btn_tv.setOnClickListener {
            navigateTo(
                R.id.categoryImagesFragment,
                listOf(Pair("category", PhotoCategory.ABSTRACT()))
            )
        }
    }
}