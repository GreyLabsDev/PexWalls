package com.greylabsdev.pexwalls.presentation.photogrid

import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.mainThreadObserve
import com.greylabsdev.pexwalls.presentation.ext.shedulersSubscribe
import com.greylabsdev.pexwalls.presentation.mapper.PresentationMapper
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.DataSourceMode
import com.greylabsdev.pexwalls.presentation.paging.PagingDataSource
import com.greylabsdev.pexwalls.presentation.paging.PagingUpdater
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.concurrent.TimeUnit

class PhotoGridPagingUpdater(
    private val disposables: CompositeDisposable,
    private val photoDisplayingUseCase: PhotoDisplayingUseCase,
    private val photoCategory: PhotoCategory
) : PagingUpdater<PhotoModel>(
    pagingDataSource = PagingDataSource<PhotoModel>(DataSourceMode.LIVEDATA()),
    pagingMode = PagingMode.BY_PAGE(),
    pageSize = 15,
    currentPage = 1
) {
    override fun fetchPage() {
        photoDisplayingUseCase.getPhotosForCategory(photoCategory.name, currentPage, pageSize)
            .debounce(400, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .map { it.map { photoEntity ->  PresentationMapper.mapToPhotoModel(photoEntity)} }
            .shedulersSubscribe()
            .mainThreadObserve()
            .doOnSubscribe { pagingDataSource.addFooter("","") }
            .subscribeBy(
                onNext = {photos ->
                    pagingDataSource.removeFooter()
                    pushToDataSource(mapToItems(photos))
                    updateCurrentPage(photos.size)
                },
                onError = { Timber.e(it) },
                onComplete = {}
            )
            .addTo(disposables)
    }
}