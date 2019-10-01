package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import androidx.lifecycle.LiveData
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.RxPagingAdapter
import com.greylabsdev.pexwalls.presentation.paging.PagingUpdater
import com.greylabsdev.pexwalls.presentation.photogrid.PhotoGridPagingUpdater
import com.greylabsdev.pexwalls.presentation.photogrid.paging_v2.PGPagingUpdater


class CategoryPhotosViewModel(
    private val photoCategory: PhotoCategory,
    private val photoDisplayingUseCase: PhotoDisplayingUseCase
) : BaseViewModel() {

    val photos: LiveData<List<PhotoModel>>
        get() = photoGridPagingUpdater.pagingDataSource.itemsChannelLiveData

    var photoGridDataSource = RxPagingAdapter.DataSource().apply {
        pagingUpdater = PhotoGridPagingUpdater(
            disposables,
            photoCategory,
            photoDisplayingUseCase
        ).setup(offset = 1, count = 10)
    }

    var photoGridPagingUpdater: PagingUpdater<PhotoModel> = PGPagingUpdater(
        disposables,
        photoDisplayingUseCase,
        photoCategory
    )
}