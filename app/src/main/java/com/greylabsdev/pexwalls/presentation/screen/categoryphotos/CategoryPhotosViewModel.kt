package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.mainThreadObserve
import com.greylabsdev.pexwalls.presentation.ext.shedulersSubscribe
import com.greylabsdev.pexwalls.presentation.ext.then
import com.greylabsdev.pexwalls.presentation.mapper.PresentationMapper
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.paging.RxPagingAdapter
import com.greylabsdev.pexwalls.presentation.photogrid.PhotoGridPagingUpdater
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class CategoryPhotosViewModel(
    private val photoCategory: PhotoCategory,
    private val photoDisplayingUseCase: PhotoDisplayingUseCase
) : BaseViewModel() {

    private val _photos: MutableLiveData<List<PhotoModel>> = MutableLiveData()
    val photos: LiveData<List<PhotoModel>>
        get() = _photos

    var photoGridDataSource = RxPagingAdapter.DataSource().apply {
        pagingUpdater = PhotoGridPagingUpdater(
            disposables,
            photoCategory,
            photoDisplayingUseCase
        ).setup(offset = 1, count = 10)
    }

    fun getInitialPhotosByCategory() {
        photoGridDataSource.items.isEmpty().then {
            photoGridDataSource.pagingUpdater?.loadNewItems()
        }
    }
}