package com.greylabsdev.pexwalls.presentation.screen.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.greylabsdev.pexwalls.domain.usecase.PhotoDownloadingUseCase
import com.greylabsdev.pexwalls.domain.usecase.PhotoFavoritesUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.ext.mainThreadObserve
import com.greylabsdev.pexwalls.presentation.ext.schedulersSubscribe
import com.greylabsdev.pexwalls.presentation.mapper.PresentationMapper
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber

class PhotoViewModel(
    private val photoDownloadingUseCase: PhotoDownloadingUseCase,
    private val favoritesUseCase: PhotoFavoritesUseCase,
    private val photoModel: PhotoModel
) : BaseViewModel() {

    private var _isPhotoFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val isPhotoFavorite: LiveData<Boolean>
        get() = _isPhotoFavorite

    init {
        checkIfPhotoInFavorites()
    }

    fun downloadPhoto(useOriginalResolution: Boolean = false, setAsWallpaper: Boolean = false) {
        photoDownloadingUseCase.callManagerToDownloadPhoto(
            author = photoModel.photographer,
            postfix = "${photoModel.id}${if (useOriginalResolution) "_original" else "_wallpaper"}",
            baseLink = photoModel.bigPhotoUrl,
            originalResolution = if (useOriginalResolution) Pair(photoModel.width, photoModel.height)
                else null,
            setAsWallpaper = setAsWallpaper
        ).schedulersSubscribe()
            .mainThreadObserve()
            .doOnSubscribe {
                _progressState.value = ProgressState.INITIAL("Downloading started, you will see downloading status in notification")
            }
            .subscribeBy(
                onNext = {},
                onComplete = { _progressState.value = ProgressState.DONE("Load complete") },
                onError = { error -> Timber.e(error) }
            ).addTo(disposables)
    }

    fun switchPhotoInFavoritesState() {
        if (_isPhotoFavorite.value == true) removePhotoFromFavorites()
        else addPhotoToFavorites()
    }

    private fun addPhotoToFavorites() {
        viewModelScope.launch {
            supervisorScope {
                try {
                    favoritesUseCase.addPhotoToFavorites(PresentationMapper.mapToEntity(photoModel))
                    _isPhotoFavorite.value = true
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    private fun removePhotoFromFavorites() {
        viewModelScope.launch {
            supervisorScope {
                try {
                    favoritesUseCase.removePhotoFromFavorites(PresentationMapper.mapToEntity(photoModel))
                    _isPhotoFavorite.value = false
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    private fun checkIfPhotoInFavorites() {
        viewModelScope.launch {
            supervisorScope {
                try {
                    _isPhotoFavorite.value = favoritesUseCase.checkIfPhotoInFavorites(photoModel.id)
                } catch (e: Exception) {
                    Timber.e(e)
                    _progressState.value = ProgressState.ERROR(e.message ?: "")
                }
            }
        }
    }

    suspend inline fun CoroutineScope.executeWithCatch(
        crossinline action: suspend () -> Unit,
        crossinline onErrorAction: (Exception) -> Unit
    ) {
        supervisorScope {
            try {
                action.invoke()
            } catch (e: Exception) {
                onErrorAction.invoke(e)
            }
        }
    }
}
