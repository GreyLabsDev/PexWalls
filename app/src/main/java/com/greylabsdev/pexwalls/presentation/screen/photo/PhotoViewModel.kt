package com.greylabsdev.pexwalls.presentation.screen.photo

import com.greylabsdev.pexwalls.domain.usecase.PhotoDownloadingUseCase
import com.greylabsdev.pexwalls.domain.usecase.PhotoFavoritesUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.model.PhotoModel

class PhotoViewModel (
    private val photoDownloadingUseCase: PhotoDownloadingUseCase,
    private val favoritesUseCase: PhotoFavoritesUseCase,
    private val photoModel: PhotoModel
) : BaseViewModel() {

    fun downloadPhoto(resolution: Pair<Int, Int>) {
        photoDownloadingUseCase.callManagerToDownloadPhoto(
            autror = photoModel.photographer,
            postfix = "${photoModel.id}",
            baseLink = photoModel.bigPhotoUrl,
            resolution = resolution
        )
    }

    fun addPhotoToFavorites() {
        //TODO add impl
    }

    fun removePhotoFromFavorites() {
        //TODO add impl
    }
}