package com.greylabsdev.pexwalls.presentation.screen.favorites

import androidx.lifecycle.viewModelScope
import com.greylabsdev.pexwalls.domain.usecase.PhotoFavoritesUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.collection.PhotoPagingUpdater
import com.greylabsdev.pexwalls.presentation.collection.UpdaterType
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.PagingItem
import com.greylabsdev.pexwalls.presentation.paging.PagingUpdater
import kotlinx.coroutines.flow.StateFlow

class FavoritesViewModel(
    favoritesUseCase: PhotoFavoritesUseCase
) : BaseViewModel() {

    val photos: StateFlow<List<PagingItem<PhotoModel>>>
        get() = photoGridPagingUpdater.pagingDataSource.itemsFlow

    var photoGridPagingUpdater: PagingUpdater<PhotoModel> =
        PhotoPagingUpdater(
            photoFavoritesUseCase = favoritesUseCase,
            type = UpdaterType.FAVORITES,
            loadingListener = { _progressState.value = ProgressState.LOADING() },
            doneListener = { _progressState.value = ProgressState.DONE() },
            emptyResultListener = { _progressState.value = ProgressState.EMPTY() },
            errorListener = { error -> _progressState.value = ProgressState.ERROR(error) },
            viewModelScope = viewModelScope
        )

    fun refreshFavorites() {
        photoGridPagingUpdater.resetAndFetchAgain()
    }
}
