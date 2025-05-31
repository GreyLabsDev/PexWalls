package com.greylabsdev.pexwalls.presentation.screen.search

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.databinding.FragmentSearchBinding
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.collection.photogrid.PhotoGridPagingAdapter
import com.greylabsdev.pexwalls.presentation.collection.photogrid.PhotoItemDecoration
import com.greylabsdev.pexwalls.presentation.const.Consts
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.ext.getScreenHeightInPixels
import com.greylabsdev.pexwalls.presentation.ext.getScreenWidthInPixels
import com.greylabsdev.pexwalls.presentation.ext.hideKeyboard
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.screen.photo.PhotoFragment
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    bindingFactory = FragmentSearchBinding::inflate
) {
    override val viewModel by viewModel<SearchViewModel>()
    override val toolbarTitle: String? = null
    override val contentView: View?
        get() = binding?.contentLl
    override val placeholderView: PlaceholderView?
        get() = binding?.placeholderView

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
        binding?.searchBar?.searchFieldEdt?.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                viewModel.search(textView.text.toString())
                requireActivity().hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    override fun initViewModelObserving() {
        super.initViewModelObserving()
        viewModel.photos.observe(this, Observer { newPhoto ->
            photoGridPagingAdapter.items = newPhoto
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
                false,
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
}
