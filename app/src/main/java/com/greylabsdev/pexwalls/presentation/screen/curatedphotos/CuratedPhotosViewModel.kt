package com.greylabsdev.pexwalls.presentation.screen.curatedphotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.collection.PhotoPagingUpdater
import com.greylabsdev.pexwalls.presentation.collection.UpdaterType
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.PagingItem
import com.greylabsdev.pexwalls.presentation.paging.PagingUpdater

class CuratedPhotosViewModel(photoDisplayingUseCase: PhotoDisplayingUseCase): BaseViewModel() {

    val photos: LiveData<List<PagingItem<PhotoModel>>>
        get() = photoPagingUpdater.pagingDataSource.itemsChannelLiveData

    var photoPagingUpdater: PagingUpdater<PhotoModel> =
        PhotoPagingUpdater(
            photoDisplayingUseCase = photoDisplayingUseCase,
            type = UpdaterType.CURATED,
            emptyResultListener = {_progressState.value = ProgressState.EMPTY()},
            errorListener = { error -> _progressState.value = ProgressState.ERROR(error) },
            viewModelScope = viewModelScope
        )

    fun repeatFetch() {
        _progressState.value = ProgressState.DONE()
        photoPagingUpdater.fetchPage(false)
    }
}