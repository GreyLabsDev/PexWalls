package com.greylabsdev.pexwalls.presentation.screen.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.databinding.FragmentHomeBinding
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.const.Consts
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.screen.categoryphotos.CategoryPhotosFragment
import com.greylabsdev.pexwalls.presentation.screen.home.list.CategoryColorAdapter
import com.greylabsdev.pexwalls.presentation.screen.home.list.CategoryColorItemDecoration
import com.greylabsdev.pexwalls.presentation.screen.home.list.CategoryThemeAdapter
import com.greylabsdev.pexwalls.presentation.screen.home.list.CategoryThemeItemDecoration
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    bindingFactory = FragmentHomeBinding::inflate,
    hasToolbarBackButton = false
) {
    override val viewModel by viewModel<HomeViewModel>()
    override val toolbarTitle: String? by lazy { getString(R.string.category_toolbar_title) }
    override val placeholderView: PlaceholderView?
        get() = binding?.placeholderView
    override val contentView: View?
        get() = binding?.contentContainerLl

    private lateinit var categoryThemeAdapter: CategoryThemeAdapter
    private lateinit var categoryColorAdapter: CategoryColorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCategoriesAdapters()
        viewModel.fetchCategories()
    }

    override fun initViews() {
        toolbarView = binding?.toolbar
        binding?.categoryThemesRv?.let { themesRv ->
            themesRv.adapter = categoryThemeAdapter
            if (themesRv.itemDecorationCount == 0) {
                themesRv.addItemDecoration(
                    CategoryThemeItemDecoration(
                        requireContext().dpToPix(Consts.SMALL_MARGIN_DP).toInt()
                    )
                )
            }
        }
        binding?.categoryColorsRv?.let { colorsRv ->
            colorsRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            colorsRv.adapter = categoryColorAdapter
            if (colorsRv.itemDecorationCount == 0) {
                colorsRv.addItemDecoration(
                    CategoryColorItemDecoration(
                        requireContext().dpToPix(Consts.DEFAULT_MARGIN_DP).toInt()
                    )
                )
            }
        }
    }

    override fun initListeners() {
        binding?.placeholderView?.onTryNowBtnClickAction = {
            viewModel.fetchCategories()
        }
    }

    override fun initViewModelObserving() {
        super.initViewModelObserving()
        viewModel.categoryThemes.observe(this, Observer { categories ->
            categoryThemeAdapter.categories = categories
        })
        viewModel.categoryColors.observe(this, Observer { categories ->
            categoryColorAdapter.categories = categories
        })
    }

    private fun initCategoriesAdapters() {
        categoryThemeAdapter = CategoryThemeAdapter(
            requireContext().dpToPix(Consts.DEFAULT_CORNER_RADIUS_DP)
        ) { selectedCategory ->
            navigateToCategory(selectedCategory.category)
        }
        categoryColorAdapter = CategoryColorAdapter(
            requireContext().dpToPix(Consts.DEFAULT_CORNER_RADIUS_DP)
        ) { selectedCategory ->
            navigateToCategory(selectedCategory.category)
        }
    }

    private fun navigateToCategory(category: PhotoCategory) {
        navigateTo(
            R.id.categoryPhotosFragment,
            listOf(Pair(CategoryPhotosFragment.ARG_KEY, category))
        )
    }
}
