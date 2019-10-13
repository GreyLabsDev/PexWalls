package com.greylabsdev.pexwalls.presentation.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.ext.mainThreadObserve
import com.greylabsdev.pexwalls.presentation.ext.schedulersSubscribe
import com.greylabsdev.pexwalls.presentation.model.CategoryModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

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
        if (_categoryThemes.value.isNullOrEmpty() && categoryColors.value.isNullOrEmpty()) {

            val categoryThemeVariants: List<PhotoCategory> = listOf<PhotoCategory>(
                PhotoCategory.ABSTRACT(),
                PhotoCategory.ANIMALS(),
                PhotoCategory.ARCHITECTURE(),
                PhotoCategory.NATURE(),
                PhotoCategory.NIGHT(),
                PhotoCategory.PORTRAITS(),
                PhotoCategory.SEA()
            )
            val categoryColorVariants: List<PhotoCategory> = listOf<PhotoCategory>(
                PhotoCategory.COLOR_WHITE(),
                PhotoCategory.COLOR_BLACK(),
                PhotoCategory.COLOR_RED(),
                PhotoCategory.COLOR_GREEN(),
                PhotoCategory.COLOR_BLUE(),
                PhotoCategory.COLOR_VIOLET(),
                PhotoCategory.COLOR_YELLOW()
            )

            val categoryThemesFetch = Observable.fromIterable(categoryThemeVariants)
                .flatMap { photoDisplayingUseCase.getPhotoCategoryCover(it) }
                .buffer(categoryThemeVariants.size)
            val categoryColorsFetch = Observable.fromIterable(categoryColorVariants)
                .flatMap { photoDisplayingUseCase.getPhotoCategoryCover(it) }
                .buffer(categoryColorVariants.size)

            Observable.zip(
                categoryThemesFetch,
                categoryColorsFetch,
                BiFunction { themes: List<CategoryModel>, colors: List<CategoryModel> ->
                    Pair(themes, colors) }
            ).schedulersSubscribe()
                .mainThreadObserve()
                .doOnSubscribe {
                    _progressState.value = ProgressState.LOADING()
                }
                .subscribeBy(
                    onNext = {categories ->
                        val themeCategories = categories.first
                        val colorCategories = categories.second
                        _categoryThemes.value = themeCategories
                        _categoryColors.value = colorCategories
                        _progressState.value = ProgressState.DONE()
                    },
                    onError = {
                        _progressState.value = ProgressState.ERROR("$it")
                    }
                )
                .addTo(disposables)
        }
    }

}