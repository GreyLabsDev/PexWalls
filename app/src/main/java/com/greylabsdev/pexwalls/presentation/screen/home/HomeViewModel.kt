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

    private var _categoryColors: MutableLiveData<List<CategoryModel>> = MutableLiveData()
    val categoryColors: LiveData<List<CategoryModel>>
        get() = _categoryColors

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
        if (_categoryColors.value.isNullOrEmpty()) {
            val categoryThemeVariants: List<PhotoCategory> = listOf<PhotoCategory>(
                PhotoCategory.COLOR_WHITE(),
                PhotoCategory.COLOR_BLACK(),
                PhotoCategory.COLOR_RED(),
                PhotoCategory.COLOR_GREEN(),
                PhotoCategory.COLOR_BLUE(),
                PhotoCategory.COLOR_VIOLET(),
                PhotoCategory.COLOR_YELLOW()
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
                        _categoryColors.value = categoriesWithPhoto
                    },
                    onError = { error ->
                        Timber.e(error)
                    }
                ).addTo(disposables)
        }
    }

}