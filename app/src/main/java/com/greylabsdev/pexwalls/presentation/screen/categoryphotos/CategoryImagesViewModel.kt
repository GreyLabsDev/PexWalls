package com.greylabsdev.pexwalls.presentation.screen.categoryphotos

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.mainThreadObserve
import com.greylabsdev.pexwalls.presentation.ext.shedulersSubscribe
import com.greylabsdev.pexwalls.presentation.mapper.PresentationMapper
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class CategoryImagesViewModel(private val photoDisplayingUseCase: PhotoDisplayingUseCase) : BaseViewModel() {

    private val _photos: MutableLiveData<List<PhotoModel>> = MutableLiveData()
    val photos: LiveData<List<PhotoModel>>
        get() = _photos

    fun getPhotosByCategory(category: PhotoCategory) {
        photoDisplayingUseCase.getPhotosForCategory(category.name, 0, 15)
            .map { it.map { photoEntity ->  PresentationMapper.mapToPhotoModel(photoEntity)} }
            .shedulersSubscribe()
            .mainThreadObserve()
            .subscribe(
                {newPhotos ->
                    _photos.value = newPhotos
                },
                {error ->
                    Timber.e(error)
                },
                {}
            )
            .addTo(disposables)
    }
}