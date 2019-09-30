package com.greylabsdev.pexwalls.presentation.mapper

import com.greylabsdev.pexwalls.domain.entity.PhotoEntity
import com.greylabsdev.pexwalls.presentation.model.PhotoModel

object PresentationMapper {
    fun mapToPhotoModel(photo: PhotoEntity): PhotoModel {
        return PhotoModel(
            photo.id,
            photo.src.large,
            photo.photographer,
            photo.photographerUrl
        )
    }
}