package com.greylabsdev.pexwalls.presentation.screen.photo

import androidx.lifecycle.viewModelScope
import com.greylabsdev.pexwalls.domain.usecase.PhotoDownloadingUseCase
import com.greylabsdev.pexwalls.domain.usecase.PhotoFavoritesUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.mapper.PresentationMapper
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class PhotoViewModel(
    private val photoDownloadingUseCase: PhotoDownloadingUseCase,
    private val favoritesUseCase: PhotoFavoritesUseCase,
    private val photoModel: PhotoModel
) : BaseViewModel() {

    private val _isPhotoFavorite = MutableStateFlow(false)
    val isPhotoFavorite: StateFlow<Boolean> = _isPhotoFavorite.asStateFlow()

    private var downloadJob: Job? = null

    init {
        checkIfPhotoInFavorites()
    }

    fun downloadPhoto(useOriginalResolution: Boolean = false, setAsWallpaper: Boolean = false) {
        downloadJob?.cancel()
        downloadJob = viewModelScope.launch {
            photoDownloadingUseCase.callManagerToDownloadPhotoByFlow(
                author = photoModel.photographer,
                postfix = "${photoModel.id}${if (useOriginalResolution) "_original" else "_wallpaper"}",
                baseLink = photoModel.bigPhotoUrl,
                originalResolution = if (useOriginalResolution) {
                    Pair(photoModel.width, photoModel.height)
                } else {
                    null
                },
                setAsWallpaper = setAsWallpaper
            ).collect { progress ->
                if (progress == 100) {
                    _progressState.value = ProgressState.DONE("Load complete")
                }
            }
        }
    }

    fun switchPhotoInFavoritesState() {
        if (_isPhotoFavorite.value) removePhotoFromFavorites()
        else addPhotoToFavorites()
    }

    private fun addPhotoToFavorites() {
        viewModelScope.launch {
            try {
                favoritesUseCase.addPhotoToFavorites(PresentationMapper.mapToEntity(photoModel))
                _isPhotoFavorite.value = true
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun removePhotoFromFavorites() {
        viewModelScope.launch {
            try {
                favoritesUseCase.removePhotoFromFavorites(PresentationMapper.mapToEntity(photoModel))
                _isPhotoFavorite.value = false
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun checkIfPhotoInFavorites() {
        viewModelScope.launch {
            try {
                _isPhotoFavorite.value = favoritesUseCase.checkIfPhotoInFavorites(photoModel.id)
            } catch (e: Exception) {
                Timber.e(e)
                _progressState.value = ProgressState.ERROR(e.message ?: "")
            }
        }
    }
}
