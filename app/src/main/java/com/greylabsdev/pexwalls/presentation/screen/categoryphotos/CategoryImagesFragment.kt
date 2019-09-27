package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import androidx.lifecycle.Observer
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.screen.categoryphotos.list.PhotoGridAdapter
import kotlinx.android.synthetic.main.fragment_categoryimages.*
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryImagesFragment : BaseFragment(
    layoutResId = R.layout.fragment_categoryimages,
    toolbarTitle = "Category",
    hasToolbarBackButton = true
) {
    override val viewModel by viewModel<CategoryImagesViewModel>()
    private val photoAdapter = PhotoGridAdapter()

    override fun initViews() {
        super.initViews()
        photo_grid_rv.adapter = photoAdapter
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