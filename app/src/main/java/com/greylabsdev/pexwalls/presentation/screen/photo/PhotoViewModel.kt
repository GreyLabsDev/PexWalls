package com.greylabsdev.pexwalls.presentation.screen.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
            .doOnSubscribe { _progressState.value = ProgressState.INITIAL("Downloading started, you will see downloading status in notification") }
            .subscribeBy(
                onNext = {},
                onComplete = { _progressState.value = ProgressState.DONE("Load complete") },
                onError = { error -> Timber.e(error) }
            ).addTo(disposables)
    }

    fun switchPhotoInFavoritesState() {
        if (_isPhotoFavorite.value ?: false) removePhotoFromFavorites()
        else addPhotoToFavorites()
    }

    private fun addPhotoToFavorites() {
        favoritesUseCase.addPhotoToFavorites(PresentationMapper.mapToEntity(photoModel))
            .schedulersSubscribe()
            .mainThreadObserve()
            .subscribeBy(
                onComplete = { _isPhotoFavorite.value = true },
                onError = { error -> Timber.e(error) }
            ).addTo(disposables)
    }

    private fun removePhotoFromFavorites() {
        favoritesUseCase.removePhotoFromFavorites(PresentationMapper.mapToEntity(photoModel))
            .schedulersSubscribe()
            .mainThreadObserve()
            .subscribeBy(
                onComplete = { _isPhotoFavorite.value = false },
                onError = { error -> Timber.e(error) }
            ).addTo(disposables)
    }

    private fun checkIfPhotoInFavorites() {
        favoritesUseCase.checkIfPhotoInFavorites(photoModel.id)
            .schedulersSubscribe()
            .mainThreadObserve()
            .subscribeBy(
                onSuccess = {
                    _isPhotoFavorite.value = it
                },
                onError = { error ->
                    Timber.e(error)
                    _progressState.value = ProgressState.ERROR(error.message ?: "")
                }
            ).addTo(disposables)
    }
}
