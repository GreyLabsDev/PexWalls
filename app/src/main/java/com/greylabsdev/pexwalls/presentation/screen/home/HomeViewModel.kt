package com.greylabsdev.pexwalls.presentation.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.mainThreadObserve
import com.greylabsdev.pexwalls.presentation.ext.shedulersSubscribe
import com.greylabsdev.pexwalls.presentation.model.CategoryModel
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class HomeViewModel(
    private val photoDisplayingUseCase: PhotoDisplayingUseCase
) : BaseViewModel() {

    private var _categoryThemes: MutableLiveData<List<CategoryModel>> = MutableLiveData()
    val categoryThemes: LiveData<List<CategoryModel>>
        get() = _categoryThemes

    fun fetchCategories() {
        if (_categoryThemes.value.isNullOrEmpty()) {
            val categoryThemeVariants: List<PhotoCategory> = listOf<PhotoCategory>(
                PhotoCategory.ABSTRACT(),
                PhotoCategory.ANIMALS(),
                PhotoCategory.ARCHITECTURE(),
                PhotoCategory.NATURE(),
                PhotoCategory.NIGHT(),
                PhotoCategory.PORTRAITS(),
                PhotoCategory.SEA()
            )
            Observable.fromIterable(categoryThemeVariants)
                .flatMap {
                    photoDisplayingUseCase.getPhotoCategoryCover(it)
                }
                .buffer(categoryThemeVariants.size)
                .shedulersSubscribe()
                .mainThreadObserve()
                .subscribeBy(
                    onNext = { categoriesWithPhoto ->
                        _categoryThemes.value = categoriesWithPhoto
                    },
                    onError = { error ->
                        Timber.e(error)
                    }
                ).addTo(disposables)
        }
    }

}