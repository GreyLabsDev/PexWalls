package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.*
import com.greylabsdev.pexwalls.presentation.collection.photogrid.PhotoItemDecoration
import com.greylabsdev.pexwalls.presentation.collection.photogrid.PhotoGridPagingAdapter
import kotlinx.android.synthetic.main.fragment_category_photos.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CategoryPhotosFragment : BaseFragment(
    layoutResId = R.layout.fragment_category_photos,
    hasToolbarBackButton = true
) {
    override val viewModel by viewModel<CategoryPhotosViewModel>{
        parametersOf(photoCategory)
    }
    override val toolbarTitle by lazy { photoCategory.name.capitalize() }
    override val progressBar: View by lazy { progress_bar }

    private val photoCategory by argSerializable<PhotoCategory>("category")
    private val photoCardMargin by lazy { requireActivity().dpToPix(16) }
    private val photoCardWidth by lazy { requireActivity().getScreenWidthInPixels()/2 }
    private val photoCardHeight by lazy { requireActivity().getScreenHeightInPixels()/3}

    private lateinit var photoGridPagingAdapter: PhotoGridPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPhotoGridPagingAdapter()
    }

    override fun initViews() {
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        photo_grid_rv.layoutManager = staggeredGridLayoutManager
        photo_grid_rv.adapter = photoGridPagingAdapter

        if (photo_grid_rv.itemDecorationCount == 0) {
            photo_grid_rv.addItemDecoration(
                PhotoItemDecoration(
                    photoCardMargin.toInt()
                )
            )
        }
    }

    override fun initViewModelObserving() {
        super.initViewModelObserving()
        viewModel.photos.observe(this, Observer {newPhotos ->
            photoGridPagingAdapter.items = newPhotos
        })
    }

    private fun initPhotoGridPagingAdapter() {
        photoGridPagingAdapter =
            PhotoGridPagingAdapter(
                viewModel.photoGridPagingUpdater,
                true,
                photoCardWidth,
                photoCardHeight
            )
    }
}