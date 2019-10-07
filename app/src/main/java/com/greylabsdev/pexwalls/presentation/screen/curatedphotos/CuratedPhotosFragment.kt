package com.greylabsdev.pexwalls.presentation.screen.curatedphotos

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.collection.photolist.PhotoListItemDecoration
import com.greylabsdev.pexwalls.presentation.collection.photolist.PhotoListPagingAdapter
import com.greylabsdev.pexwalls.presentation.const.Consts
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.ext.getScreenHeightInPixels
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.screen.photo.PhotoFragment
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView
import kotlinx.android.synthetic.main.fragment_curated_photos.*
import org.koin.android.viewmodel.ext.android.viewModel


class CuratedPhotosFragment : BaseFragment(
    layoutResId = R.layout.fragment_curated_photos,
    hasToolbarBackButton = true
) {
    override val viewModel by viewModel<CuratedPhotosViewModel>()
    override val placeholderView: PlaceholderView? by lazy { placeholder_view }
    override val contentView: View? by lazy { photo_list_rv }
    override val toolbarTitle: String? by lazy { getString(R.string.curated_toolbar_title) }

    private val photoCardMargin by lazy { requireActivity().dpToPix(Consts.DEFAULT_MARGIN_DP).toInt() }
    private val photoCardHeight by lazy {
        (requireActivity().getScreenHeightInPixels() * Consts.PHOTO_HEIGHT_BY_SCREEN_PERCENT).toInt()
    }

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

    override fun initListeners() {
        placeholder_view.onTryNowBtnClickAction = { viewModel.repeatFetch() }
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
            photoCardHeight,
            Consts.DEFAULT_CORNER_RADIUS_DP
        ) {
            navigateToFullPhoto(it)
        }
    }

    private fun navigateToFullPhoto(photoModel: PhotoModel) {
        navigateTo(R.id.photoFragment, listOf(Pair(PhotoFragment.ARG_KEY, photoModel)))
    }
}