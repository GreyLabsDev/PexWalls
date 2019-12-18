package com.greylabsdev.pexwalls.presentation.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.model.CategoryModel
import java.lang.Exception
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

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

            val categoryThemeVariants: List<PhotoCategory> = listOf(
                PhotoCategory.ABSTRACT(),
                PhotoCategory.ANIMALS(),
                PhotoCategory.ARCHITECTURE(),
                PhotoCategory.NATURE(),
                PhotoCategory.NIGHT(),
                PhotoCategory.PORTRAITS(),
                PhotoCategory.SEA()
            )
            val categoryColorVariants: List<PhotoCategory> = listOf(
                PhotoCategory.COLOR_WHITE(),
                PhotoCategory.COLOR_BLACK(),
                PhotoCategory.COLOR_RED(),
                PhotoCategory.COLOR_GREEN(),
                PhotoCategory.COLOR_BLUE(),
                PhotoCategory.COLOR_VIOLET(),
                PhotoCategory.COLOR_YELLOW()
            )

            viewModelScope.launch {
                supervisorScope {
                    try {
                        _progressState.value = ProgressState.LOADING()
                        val themes = async(IO) {
                            categoryThemeVariants.mapNotNull { photoDisplayingUseCase.getPhotoCategoryCover(it) }
                        }
                        val colors = async(IO) {
                            categoryColorVariants.mapNotNull { photoDisplayingUseCase.getPhotoCategoryCover(it) }
                        }
                        doAfterAllAwait(themes.await(), colors.await()) { values ->
                            _categoryThemes.value = values[0]
                            _categoryColors.value = values[1]
                        }
                        _progressState.value = ProgressState.DONE()
                    } catch (ex: Exception) {
                        _progressState.value = ProgressState.ERROR("$ex")
                    }
                }
            }
        }
    }
}

suspend fun <T> CoroutineScope.doAfterAllAwait(vararg awaitValues: T, nextAction: (Array<out T>) -> Unit) {
    nextAction.invoke(awaitValues)
}

suspend fun ViewModel.runOnMain(vararg functionsToRun: () -> Unit) {
    withContext(Main) { functionsToRun.forEach { it.invoke() } }
}

suspend fun ViewModel.runOnMain(functionToRun: () -> Unit) {
    withContext(Main) { functionToRun.invoke() }
}
