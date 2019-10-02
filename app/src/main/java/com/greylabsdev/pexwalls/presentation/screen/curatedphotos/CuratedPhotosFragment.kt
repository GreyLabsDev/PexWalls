package com.greylabsdev.pexwalls.presentation.screen.curatedphotos

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.collection.photogrid.PhotoGridPagingAdapter
import com.greylabsdev.pexwalls.presentation.collection.photolist.PhotoListItemDecoration
import com.greylabsdev.pexwalls.presentation.collection.photolist.PhotoListPagingAdapter
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.ext.getScreenHeightInPixels
import kotlinx.android.synthetic.main.fragment_curated_photos.*
import org.koin.android.viewmodel.ext.android.viewModel


class CuratedPhotosFragment : BaseFragment(
    layoutResId = R.layout.fragment_curated_photos,
    hasToolbarBackButton = true
) {
    override val viewModel by viewModel<CuratedPhotosViewModel>()
    override val progressBar: View? = null
    override val toolbarTitle: String? = "Curated"

    private val photoCardMargin by lazy { requireActivity().dpToPix(16).toInt() }
    private val photoCardHeight by lazy { (requireActivity().getScreenHeightInPixels()*0.55).toInt() }

    private lateinit var photoListPagingAdapter: PhotoListPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPhotoListPagingAdapter()
    }

    override fun initViews() {
        photo_list_rv.adapter = photoListPagingAdapter
        if (photo_list_rv.itemDecorationCount == 0) {
            photo_list_rv.addItemDecoration(
                PhotoListItemDecoration(photoCardMargin)
            )
        }
    }

    override fun initViewModelObserving() {
        super.initViewModelObserving()
        viewModel.photos.observe(this, Observer {newPhoto ->
            photoListPagingAdapter.items = newPhoto
        })
    }

    private fun initPhotoListPagingAdapter() {
        photoListPagingAdapter = PhotoListPagingAdapter(
            viewModel.photoPagingUpdater,
            true,
            photoCardHeight
        )
    }

}