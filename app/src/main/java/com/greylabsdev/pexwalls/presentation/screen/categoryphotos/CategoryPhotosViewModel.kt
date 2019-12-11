package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.PagingItem
import com.greylabsdev.pexwalls.presentation.paging.PagingUpdater
import com.greylabsdev.pexwalls.presentation.collection.PhotoPagingUpdater
import com.greylabsdev.pexwalls.presentation.collection.UpdaterType


class CategoryPhotosViewModel(
    photoDisplayingUseCase: PhotoDisplayingUseCase,
    photoCategory: PhotoCategory
) : BaseViewModel() {

    val photos: LiveData<List<PagingItem<PhotoModel>>>
        get() = photoGridPagingUpdater.pagingDataSource.itemsChannelLiveData

    var photoGridPagingUpdater: PagingUpdater<PhotoModel> =
        PhotoPagingUpdater(
            photoDisplayingUseCase = photoDisplayingUseCase,
            type = UpdaterType.CATEGORY,
            photoCategory = photoCategory,
            emptyResultListener = {_progressState.value = ProgressState.EMPTY() },
            errorListener = { error -> _progressState.value = ProgressState.ERROR(error) },
            viewModelScope = viewModelScope
        )

    fun repeatFetch() {
        _progressState.value = ProgressState.DONE()
        photoGridPagingUpdater.fetchPage(false)
    }
}