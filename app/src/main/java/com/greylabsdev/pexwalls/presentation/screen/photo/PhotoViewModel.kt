package com.greylabsdev.pexwalls.presentation.screen.photo

import com.greylabsdev.pexwalls.domain.usecase.PhotoDownloadingUseCase
import com.greylabsdev.pexwalls.presentation.base.BaseViewModel
import com.greylabsdev.pexwalls.presentation.model.PhotoModel

class PhotoViewModel (
    private val photoDownloadingUseCase: PhotoDownloadingUseCase,
    val photoModel: PhotoModel
) : BaseViewModel() {

}