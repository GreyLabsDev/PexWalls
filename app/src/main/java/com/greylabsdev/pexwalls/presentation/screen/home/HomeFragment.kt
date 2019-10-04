package com.greylabsdev.pexwalls.presentation.screen.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.const.Consts
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.screen.home.list.CategoryColorAdapter
import com.greylabsdev.pexwalls.presentation.screen.home.list.CategoryColorItemDecoration
import com.greylabsdev.pexwalls.presentation.screen.home.list.CategoryThemeAdapter
import com.greylabsdev.pexwalls.presentation.screen.home.list.CategoryThemeItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(
    layoutResId = R.layout.fragment_home,
    hasToolbarBackButton = false
) {
    override val viewModel by viewModel<HomeViewModel>()
    override val toolbarTitle: String? by lazy { getString(R.string.category_toolbar_title) }
    override val progressView: View? by lazy { placeholder_container_ll }
    override val contentView: View? by lazy { content_container_ll }

    private lateinit var categoryThemeAdapter: CategoryThemeAdapter
    private lateinit var categoryColorAdapter: CategoryColorAdapter

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
        category_colors_rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        category_colors_rv.adapter = categoryColorAdapter
        if (category_colors_rv.itemDecorationCount == 0) {
            category_colors_rv.addItemDecoration(
                CategoryColorItemDecoration(requireContext().dpToPix(Consts.DEFAULT_MARGIN_DP).toInt())
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
        viewModel.categoryColors.observe(this, Observer {categories ->
            categoryColorAdapter.categories = categories
        })
    }

    private fun initCategoriesAdapters() {
        categoryThemeAdapter = CategoryThemeAdapter(
            requireContext().dpToPix(Consts.DEFAULT_CORNER_RADIUS_DP)
        ) {selectedCategory ->
            navigateToCategory(selectedCategory.category)
        }
        categoryColorAdapter = CategoryColorAdapter(
            requireContext().dpToPix(Consts.DEFAULT_CORNER_RADIUS_DP)
        ) {selectedCategory ->
            navigateToCategory(selectedCategory.category)
        }
    }

    private fun navigateToCategory(category: PhotoCategory) {
        navigateTo(
            R.id.categoryPhotosFragment,
            listOf(Pair("category", category))
        )
    }
}