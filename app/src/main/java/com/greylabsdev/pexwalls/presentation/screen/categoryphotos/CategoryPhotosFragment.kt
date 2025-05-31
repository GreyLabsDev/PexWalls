package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.databinding.FragmentCategoryPhotosBinding
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.collection.photogrid.PhotoGridPagingAdapter
import com.greylabsdev.pexwalls.presentation.collection.photogrid.PhotoItemDecoration
import com.greylabsdev.pexwalls.presentation.const.Consts
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.argSerializable
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.ext.getScreenHeightInPixels
import com.greylabsdev.pexwalls.presentation.ext.getScreenWidthInPixels
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.screen.photo.PhotoFragment
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CategoryPhotosFragment : BaseFragment<FragmentCategoryPhotosBinding>(
    bindingFactory = FragmentCategoryPhotosBinding::inflate,
    hasToolbarBackButton = true
) {
    override val viewModel by viewModel<CategoryPhotosViewModel> {
        parametersOf(photoCategory)
    }

    override val toolbarTitle by lazy { photoCategory.name.capitalize() }
    override val placeholderView: PlaceholderView?
        get() = binding?.placeholderView
    override val contentView: View?
        get() = binding?.photoGridRv

    private val photoCategory by argSerializable<PhotoCategory>(ARG_KEY)

    private val photoCardMargin by lazy { requireActivity().dpToPix(Consts.DEFAULT_MARGIN_DP) }
    private val photoCardWidth by lazy { requireActivity().getScreenWidthInPixels() / 2 }
    private val photoCardHeight by lazy { requireActivity().getScreenHeightInPixels() / 3 }

    private lateinit var photoGridPagingAdapter: PhotoGridPagingAdapter
    private var recyclerState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPhotoGridPagingAdapter()
    }

    override fun initViews() {
        toolbarView = binding?.toolbar
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding?.photoGridRv?.let { photoGrid ->
            photoGrid.layoutManager = staggeredGridLayoutManager
            photoGrid.adapter = photoGridPagingAdapter

            if (photoGrid.itemDecorationCount == 0) {
                photoGrid.addItemDecoration(
                    PhotoItemDecoration(
                        photoCardMargin.toInt()
                    )
                )
            }
        }

    }

    override fun initListeners() {
        binding?.placeholderView?.onTryNowBtnClickAction = { viewModel.repeatFetch() }
    }

    override fun initViewModelObserving() {
        super.initViewModelObserving()
        viewModel.photos.observe(this, Observer { newPhotos ->
            photoGridPagingAdapter.items = newPhotos
        })
    }

    override fun onPause() {
        super.onPause()
        binding?.photoGridRv?.layoutManager?.onSaveInstanceState()?.let { state ->
            recyclerState = state
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerState?.let { state ->
            binding?.photoGridRv?.layoutManager?.onRestoreInstanceState(state)
        }
    }

    private fun initPhotoGridPagingAdapter() {
        photoGridPagingAdapter =
            PhotoGridPagingAdapter(
                viewModel.photoGridPagingUpdater,
                true,
                photoCardWidth,
                photoCardHeight,
                Consts.DEFAULT_CORNER_RADIUS_DP
            ) { photo ->
                navigateToFullPhoto(photo)
            }
    }

    private fun navigateToFullPhoto(photoModel: PhotoModel) {
        navigateTo(R.id.photoFragment, listOf(Pair(PhotoFragment.ARG_KEY, photoModel)))
    }

    companion object {
        const val ARG_KEY = "category"
    }
}
