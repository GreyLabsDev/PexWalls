package com.greylabsdev.pexwalls.presentation.photogrid

import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.mainThreadObserve
import com.greylabsdev.pexwalls.presentation.ext.shedulersSubscribe
import com.greylabsdev.pexwalls.presentation.mapper.PresentationMapper
import com.greylabsdev.pexwalls.presentation.paging.PagingLoadingState
import com.greylabsdev.pexwalls.presentation.paging.RxPagingAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class PhotoGridPagingUpdater(
    disposables: CompositeDisposable,
    private val category: PhotoCategory,
    private val photoDisplayingUseCase: PhotoDisplayingUseCase
) : RxPagingAdapter.RxPagingUpdater(disposables) {

    override fun loadNewItems() {
        photoDisplayingUseCase.getPhotosForCategory(category.name, currentPosition, count)
            .map { it.map { photoEntity ->  PresentationMapper.mapToPhotoModel(photoEntity)} }
            .shedulersSubscribe()
            .mainThreadObserve()
            .doOnSubscribe { loadingStateFlow.onNext(PagingLoadingState.LOADING) }
            .subscribeBy(
                onNext = {
                    //fix mutable lists to lists
                    loadingStateFlow.onNext(PagingLoadingState.DONE)
                    itemsFlow.onNext(it.toMutableList())
                    updateCurrentPage()
                },
                onError = {loadingStateFlow.onNext(PagingLoadingState.ERROR)},
                onComplete = {}
            )
            .addTo(updaterDisposables)
    }
}