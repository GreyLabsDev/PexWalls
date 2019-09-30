package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.argSerializable
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.ext.getScreenHeightInPixels
import com.greylabsdev.pexwalls.presentation.ext.getScreenWidthInPixels
import com.greylabsdev.pexwalls.presentation.photogrid.PhotoItemDecoration
import com.greylabsdev.pexwalls.presentation.photogrid.PhotoGridPagingAdapter
import kotlinx.android.synthetic.main.fragment_categoryimages.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CategoryPhotosFragment : BaseFragment(
    layoutResId = R.layout.fragment_categoryimages,
    hasToolbarBackButton = true
) {
    override val viewModel by viewModel<CategoryPhotosViewModel>{
        parametersOf(photoCategory)
    }
    override val toolbarTitle by lazy { photoCategory.name.capitalize() }
    override val progressBar: View by lazy { progress_bar }

    private val photoCategory by argSerializable<PhotoCategory>("category")
    private val imageCardMargin by lazy { requireActivity().dpToPix(16) }
    private val imageCardWidth by lazy { requireActivity().getScreenWidthInPixels()/2 }
    private val imageCardHeight by lazy { requireActivity().getScreenHeightInPixels()/3}

    override fun initViews() {
        photo_grid_rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
            gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        }

//        photo_grid_rv.adapter = photoAdapter
//        photo_grid_rv.addItemDecoration(
//            PhotoItemDecoration(
//                imageCardMargin.toInt()
//            )
//        )

        initRxPagingRv()
    }

    override fun doInitialCalls() {
        viewModel.getInitialPhotosByCategory()
    }

    private fun initRxPagingRv() {
        val photoGridPagingAdapter = PhotoGridPagingAdapter(imageCardWidth, imageCardHeight)
        photoGridPagingAdapter.dataSource = viewModel.photoGridDataSource
        photo_grid_rv.adapter = photoGridPagingAdapter
        photo_grid_rv.addItemDecoration(
            PhotoItemDecoration(
                imageCardMargin.toInt()
            )
        )
    }
}