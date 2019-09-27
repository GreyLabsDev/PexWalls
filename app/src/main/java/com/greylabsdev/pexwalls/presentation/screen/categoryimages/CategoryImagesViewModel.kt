package com.greylabsdev.pexwalls.presentation.screen.categoryimages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.mainThreadObserve
import com.greylabsdev.pexwalls.presentation.ext.shedulersSubscribe
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class CategoryImagesViewModel(private val photoDisplayingUseCase: PhotoDisplayingUseCase) : BaseViewModel() {

    private val _photos: MutableLiveData<List<PhotoModel>> = MutableLiveData()
    val photos: LiveData<List<PhotoModel>>
        get() = _photos

    fun getPhotosByCategory(category: PhotoCategory) {
        photoDisplayingUseCase.getPhotosForCategory(category.name, 0, 15)
            .shedulersSubscribe()
            .mainThreadObserve()
            .doOnError {
                //handle error
            }
            .subscribeBy {
                //sen to ui
            }
            .addTo(disposables)
    }
}