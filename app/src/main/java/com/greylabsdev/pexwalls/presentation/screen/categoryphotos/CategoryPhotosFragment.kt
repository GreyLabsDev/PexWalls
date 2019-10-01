package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.*
import com.greylabsdev.pexwalls.presentation.photogrid.PhotoItemDecoration
import com.greylabsdev.pexwalls.presentation.photogrid.paging_v2.PGPagingAdapter
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

    private lateinit var photoGridPagingAdapterV2: PGPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPhotoGridAdapter()
    }

    override fun initViews() {
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        photo_grid_rv.layoutManager = staggeredGridLayoutManager
        photo_grid_rv.adapter = photoGridPagingAdapterV2

        if (photo_grid_rv.itemDecorationCount == 0) {
            photo_grid_rv.addItemDecoration(
                PhotoItemDecoration(
                    imageCardMargin.toInt()
                )
            )
        }
    }

    override fun initViewModelObserving() {
        super.initViewModelObserving()
        viewModel.photos.observe(this, Observer {newPhotos ->
            photoGridPagingAdapterV2.items = newPhotos
        })
    }

    private fun initPhotoGridAdapter() {
        photoGridPagingAdapterV2 = PGPagingAdapter(viewModel.photoGridPagingUpdater, true)
    }
}