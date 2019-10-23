package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.*
import com.greylabsdev.pexwalls.presentation.collection.photogrid.PhotoItemDecoration
import com.greylabsdev.pexwalls.presentation.collection.photogrid.PhotoGridPagingAdapter
import com.greylabsdev.pexwalls.presentation.const.Consts
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.screen.photo.PhotoFragment
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView
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
    override val placeholderView: PlaceholderView by lazy { placeholder_view }
    override val contentView: View? by lazy { photo_grid_rv }

    private val photoCategory by argSerializable<PhotoCategory>(ARG_KEY)

    private val photoCardMargin by lazy { requireActivity().dpToPix(Consts.DEFAULT_MARGIN_DP) }
    private val photoCardWidth by lazy { requireActivity().getScreenWidthInPixels()/2 }
    private val photoCardHeight by lazy { requireActivity().getScreenHeightInPixels()/3}

    private lateinit var photoGridPagingAdapter: PhotoGridPagingAdapter
    private var recyclerState: Parcelable? = null

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

    override fun initListeners() {
        placeholder_view.onTryNowBtnClickAction = { viewModel.repeatFetch() }
    }

    override fun initViewModelObserving() {
        super.initViewModelObserving()
        viewModel.photos.observe(this, Observer {newPhotos ->
            photoGridPagingAdapter.items = newPhotos
        })
    }

    override fun onPause() {
        super.onPause()
        photo_grid_rv.layoutManager?.onSaveInstanceState()?.let {state ->
            recyclerState = state
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerState?.let {state ->
            photo_grid_rv.layoutManager?.onRestoreInstanceState(state)
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