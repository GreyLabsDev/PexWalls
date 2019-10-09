package com.greylabsdev.pexwalls.presentation.screen.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greylabsdev.pexwalls.domain.usecase.PhotoDownloadingUseCase
import com.greylabsdev.pexwalls.domain.usecase.PhotoFavoritesUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.ext.mainThreadObserve
import com.greylabsdev.pexwalls.presentation.ext.shedulersSubscribe
import com.greylabsdev.pexwalls.presentation.mapper.PresentationMapper
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Timed
import timber.log.Timber

class PhotoViewModel (
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

    fun downloadPhoto(resolution: Pair<Int, Int>) {
        photoDownloadingUseCase.callManagerToDownloadPhoto(
            autror = photoModel.photographer,
            postfix = "${photoModel.id}",
            baseLink = photoModel.bigPhotoUrl,
            resolution = resolution
        )
    }

    fun switchPhotoInFavoritesState() {
        if (_isPhotoFavorite.value ?: false) removePhotoFromFavorites()
        else addPhotoToFavorites()
    }

    private fun addPhotoToFavorites() {
        favoritesUseCase.addPhotoToFavorites(PresentationMapper.mapToEntity(photoModel))
            .shedulersSubscribe()
            .mainThreadObserve()
            .subscribeBy(
                onComplete = {_isPhotoFavorite.value = true},
                onError = {}
            ).addTo(disposables)
    }

    private fun removePhotoFromFavorites() {
        favoritesUseCase.removePhotoFromFavorites(PresentationMapper.mapToEntity(photoModel))
            .shedulersSubscribe()
            .mainThreadObserve()
            .subscribeBy(
                onComplete = {_isPhotoFavorite.value = false},
                onError = {}
            ).addTo(disposables)
    }

    private fun checkIfPhotoInFavorites() {
        favoritesUseCase.checkIfPhotoInFavorites(photoModel.id)
            .shedulersSubscribe()
            .mainThreadObserve()
            .subscribeBy(
                onSuccess = {
                    _isPhotoFavorite.value = it
                },
                onError = {error ->
                    Timber.e(error)
                    _progressState.value = ProgressState.ERROR(error.message ?: "")
                }
            ).addTo(disposables)
    }
}