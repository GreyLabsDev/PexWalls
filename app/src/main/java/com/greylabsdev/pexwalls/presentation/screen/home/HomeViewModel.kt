package com.greylabsdev.pexwalls.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.greylabsdev.pexwalls.domain.usecase.PhotoDisplayingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.model.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class HomeViewModel(
    private val photoDisplayingUseCase: PhotoDisplayingUseCase
) : BaseViewModel() {

    private val _categoryThemes = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categoryThemes: StateFlow<List<CategoryModel>> = _categoryThemes.asStateFlow()

    private val _categoryColors = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categoryColors: StateFlow<List<CategoryModel>> = _categoryColors.asStateFlow()

    fun fetchCategories() {
        if (_categoryThemes.value.isEmpty() && _categoryColors.value.isEmpty()) {
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
                        val themes = async(Dispatchers.IO) {
                            categoryThemeVariants.mapNotNull {
                                photoDisplayingUseCase.getPhotoCategoryCover(it)
                            }
                        }
                        val colors = async(Dispatchers.IO) {
                            categoryColorVariants.mapNotNull {
                                photoDisplayingUseCase.getPhotoCategoryCover(it)
                            }
                        }
                        _categoryThemes.value = themes.await()
                        _categoryColors.value = colors.await()
                        _progressState.value = ProgressState.DONE()
                    } catch (ex: Exception) {
                        _progressState.value = ProgressState.ERROR("$ex")
                    }
                }
            }
        }
    }
}
