package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.ext.getScreenHeightInPixels
import com.greylabsdev.pexwalls.presentation.ext.getScreenWidthInPixels
import com.greylabsdev.pexwalls.presentation.screen.categoryphotos.list.PhotoGridAdapter
import com.greylabsdev.pexwalls.presentation.screen.categoryphotos.list.PhotoItemDecoration
import kotlinx.android.synthetic.main.fragment_categoryimages.*
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryImagesFragment : BaseFragment(
    layoutResId = R.layout.fragment_categoryimages,
    toolbarTitle = "Category",
    hasToolbarBackButton = true
) {
    override val viewModel by viewModel<CategoryImagesViewModel>()
    private val imageCardMargin by lazy { requireActivity().dpToPix(16) }
    private val imageCardWidth by lazy {
        (requireActivity().getScreenWidthInPixels()/2 - 0).toInt()
    }
    private val imageCardHeight by lazy {
        requireActivity().getScreenHeightInPixels()/3
    }
    private val photoAdapter by lazy {
        PhotoGridAdapter(imageCardWidth, imageCardHeight)
    }

    override fun initViews() {
        super.initViews()
        photo_grid_rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
            gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        }
        photo_grid_rv.adapter = photoAdapter
        photo_grid_rv.addItemDecoration(PhotoItemDecoration(imageCardMargin.toInt()))
    }

    override fun initViewModelObserving() {
        super.initViewModelObserving()
        viewModel.photos.observe(this, Observer {newPhotos ->
            photoAdapter.updatePhotos(newPhotos)
        })
    }

    override fun doInitialCalls() {
        viewModel.getPhotosByCategory(PhotoCategory.ABSTRACT())
    }
}