package com.greylabsdev.pexwalls.presentation.screen.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.const.Consts
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.ext.setNavigationClickListener
import com.greylabsdev.pexwalls.presentation.screen.home.list.CategoryThemeAdapter
import com.greylabsdev.pexwalls.presentation.screen.home.list.CategoryThemeItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(
    layoutResId = R.layout.fragment_home,
    hasToolbarBackButton = false
) {
    override val viewModel by viewModel<HomeViewModel>()
    override val toolbarTitle = "Home"
    override val progressBar: View? = null

    private lateinit var categoryThemeAdapter: CategoryThemeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCategoriesAdapters()
        viewModel.fetchCategories()
    }

    override fun initViews() {
        category_themes_rv.adapter = categoryThemeAdapter
        if (category_themes_rv.itemDecorationCount == 0) {
            category_themes_rv.addItemDecoration(
                CategoryThemeItemDecoration(requireContext().dpToPix(Consts.SMALL_MARGIN_DP).toInt())
            )
        }
    }

    override fun initListeners() {
        curated_btn_tv.setOnClickListener{
            navigateTo(R.id.curatedPhotosFragment)
        }
    }

    override fun initViewModelObserving() {
        super.initViewModelObserving()
        viewModel.categoryThemes.observe(this, Observer {categories ->
            categoryThemeAdapter.categories = categories
        })
    }

    private fun initCategoriesAdapters() {
        categoryThemeAdapter = CategoryThemeAdapter(
            requireContext().dpToPix(Consts.DEFAULT_CORNER_RADIUS_DP)
        ) {selectedCategory ->
            navigateTo(
                R.id.categoryPhotosFragment,
                listOf(Pair("category", selectedCategory.category))
            )
        }
        //TODO add color category adapter
    }
}