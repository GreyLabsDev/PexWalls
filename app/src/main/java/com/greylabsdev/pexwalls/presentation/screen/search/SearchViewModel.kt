package com.greylabsdev.pexwalls.presentation.screen.search

import androidx.lifecycle.viewModelScope
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.collection.PhotoPagingUpdater
import com.greylabsdev.pexwalls.presentation.collection.UpdaterType
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.PagingItem
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(
    private val photoDisplayingUseCase: PhotoDisplayingUseCase
) : BaseViewModel() {

    val photos: StateFlow<List<PagingItem<PhotoModel>>>
        get() = photoGridPagingUpdater.pagingDataSource.itemsFlow

    var photoGridPagingUpdater: PhotoPagingUpdater =
        PhotoPagingUpdater(
            photoDisplayingUseCase = photoDisplayingUseCase,
            type = UpdaterType.SEARCH,
            loadingListener = { _progressState.value = ProgressState.LOADING() },
            doneListener = { _progressState.value = ProgressState.DONE() },
            emptyResultListener = { _progressState.value = ProgressState.EMPTY() },
            errorListener = { error -> _progressState.value = ProgressState.ERROR(error) },
            viewModelScope = viewModelScope
        )

    init {
        _progressState.value = ProgressState.INITIAL()
    }

    fun search(searchQuery: String) {
        photoGridPagingUpdater.searchQuery = searchQuery
        photoGridPagingUpdater.resetAndFetchAgain()
    }
}
