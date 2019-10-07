package com.greylabsdev.pexwalls.presentation.screen.search

import androidx.lifecycle.LiveData
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.collection.PhotoPagingUpdater
import com.greylabsdev.pexwalls.presentation.collection.UpdaterType
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.PagingItem

class SearchViewModel(
    private val photoDisplayingUseCase: PhotoDisplayingUseCase
)  : BaseViewModel() {

    val photos: LiveData<List<PagingItem<PhotoModel>>>
        get() = photoGridPagingUpdater.pagingDataSource.itemsChannelLiveData

    var photoGridPagingUpdater: PhotoPagingUpdater =
        PhotoPagingUpdater(
            disposables = disposables,
            photoDisplayingUseCase = photoDisplayingUseCase,
            type = UpdaterType.SEARCH,
            doneListener = {_progressState.value = ProgressState.DONE()},
            emptyResultListener = {_progressState.value = ProgressState.EMPTY()},
            errorListener = { error -> _progressState.value = ProgressState.ERROR(error) }
        )

    init {
        _progressState.value = ProgressState.INITIAL()
    }

    fun search(searchQuery: String) {
        photoGridPagingUpdater.searchQuery = searchQuery
        photoGridPagingUpdater.resetAndFetchAgain()
    }
}