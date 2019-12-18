package com.greylabsdev.pexwalls.presentation.collection

import com.greylabsdev.pexwalls.domain.entity.PhotoEntity
import com.greylabsdev.pexwalls.domain.entity.PhotoFavoriteEntity
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.domain.usecase.PhotoFavoritesUseCase
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.mapper.PresentationMapper
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.DataSourceMode
import com.greylabsdev.pexwalls.presentation.paging.PagingDataSource
import com.greylabsdev.pexwalls.presentation.paging.PagingUpdater
import java.io.IOException
import java.lang.Exception
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class PhotoPagingUpdater(
    private val type: UpdaterType,
    private val photoDisplayingUseCase: PhotoDisplayingUseCase? = null,
    private val photoFavoritesUseCase: PhotoFavoritesUseCase? = null,
    private val photoCategory: PhotoCategory? = null,
    private val loadingListener: (() -> Unit)? = null,
    private val doneListener: (() -> Unit)? = null,
    private val errorListener: ((error: String) -> Unit)? = null,
    private val emptyResultListener: (() -> Unit)? = null,
    var searchQuery: String? = null,
    private val viewModelScope: CoroutineScope
) : PagingUpdater<PhotoModel>(
    pagingDataSource = PagingDataSource(DataSourceMode.LIVEDATA()),
    pagingMode = PagingMode.BY_PAGE(),
    pageSize = 15,
    currentPage = 1
) {

    override fun fetchPage(usePageUpdate: Boolean) {
        when (type) {
            UpdaterType.SEARCH -> {
                searchQuery?.let { query ->
                    viewModelScope.launch {
                        delay(250)
                        if (currentPage == initialPage) loadingListener?.invoke()
                        pagingDataSource.addFooter("", "")
                        try {
                            proceedPhotosData(
                                remotePhotosToProceed = photoDisplayingUseCase?.searchPhotos(query, currentPage, pageSize),
                                usePageUpdate = usePageUpdate
                            )
                        } catch (ex: IOException) {
                            Timber.e(ex)
                            errorListener?.invoke(ex.message ?: "")
                        }
                    }
                }
            }
            UpdaterType.CATEGORY -> {
                photoCategory?.let { category ->
                    viewModelScope.launch {
                        delay(250)
                        if (currentPage == initialPage) loadingListener?.invoke()
                        pagingDataSource.addFooter("", "")
                        try {
                            proceedPhotosData(
                                remotePhotosToProceed = photoDisplayingUseCase?.getPhotosForCategory(category.name, currentPage, pageSize),
                                usePageUpdate = usePageUpdate
                            )
                        } catch (ex: IOException) {
                            Timber.e(ex)
                            errorListener?.invoke(ex.message ?: "")
                        }
                    }
                }
            }
            UpdaterType.CURATED -> {
                viewModelScope.launch {
                    delay(250)
                    if (currentPage == initialPage) loadingListener?.invoke()
                    pagingDataSource.addFooter("", "")
                    try {
                        proceedPhotosData(
                            remotePhotosToProceed = photoDisplayingUseCase?.getCuratedPhotos(currentPage, pageSize),
                            usePageUpdate = usePageUpdate
                        )
                    } catch (ex: IOException) {
                        Timber.e(ex)
                        errorListener?.invoke(ex.message ?: "")
                    }
                }
            }
            UpdaterType.FAVORITES -> {
                viewModelScope.launch {
                    delay(250)
                    if (currentPage == initialPage) loadingListener?.invoke()
                    pagingDataSource.addFooter("", "")
                    try {
                        proceedPhotosData(
                            localPhotosToProceed = photoFavoritesUseCase?.getFavoritePhotos(),
                            usePageUpdate = usePageUpdate
                        )
                    } catch (ex: Exception) {
                        Timber.e(ex)
                        errorListener?.invoke(ex.message ?: "")
                    }
                }
            }
        }
    }

    private fun proceedPhotosData(
        remotePhotosToProceed: List<PhotoEntity>? = null,
        localPhotosToProceed: List<PhotoFavoriteEntity>? = null,
        usePageUpdate: Boolean = true
    ) {
        remotePhotosToProceed?.map { photoEntity -> PresentationMapper.mapToPhotoModel(photoEntity) }
            ?.let { photos -> pushPhotosWithPagingIncrement(photos, usePageUpdate) }

        localPhotosToProceed?.map { photoEntity -> PresentationMapper.mapToPhotoModel(photoEntity) }
            ?.let { photos -> pushPhotosWithPagingIncrement(photos, usePageUpdate) }
    }

    private fun pushPhotosWithPagingIncrement(photos: List<PhotoModel>, usePageUpdate: Boolean) {
        if (currentPage == initialPage) doneListener?.invoke()
        if (photos.isNullOrEmpty()) emptyResultListener?.invoke()
        pagingDataSource.removeFooter()
        pushToDataSource(mapToItems(photos))
        if (usePageUpdate) updateCurrentPage(photos.size)
    }
}
