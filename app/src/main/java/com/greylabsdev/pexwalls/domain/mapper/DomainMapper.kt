package com.greylabsdev.pexwalls.domain.mapper

import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.PhotoDto
import com.greylabsdev.pexwalls.domain.entity.PhotoEntity
import com.greylabsdev.pexwalls.domain.entity.PhotoFavoriteEntity
import com.greylabsdev.pexwalls.domain.entity.PhotoSrcEntity

object DomainMapper {

    fun mapToPhotoEntity(src: PhotoDto): PhotoEntity {
        return PhotoEntity(
            src.height,
            src.id,
            src.photographer,
            src.photographerId,
            src.photographerUrl,
            PhotoSrcEntity(
                src.src.landscape,
                src.src.large,
                src.src.large2x,
                src.src.medium,
                src.src.original,
                src.src.portrait,
                src.src.small,
                src.src.tiny
            ),
            src.url,
            src.width
        )
    }

    fun mapToPhotoFavoriteEntity(photo: PhotoDbEntity): PhotoFavoriteEntity {
        return PhotoFavoriteEntity(
            photo.id,
            photo.normalPhotoUrl,
            photo.normalPhotoUrl,
            photo.photographer,
            photo.photographerUrl,
            photo.width,
            photo.height
        )
    }

    fun mapToDbEntity(photo: PhotoFavoriteEntity): PhotoDbEntity {
        return PhotoDbEntity(
            photo.id,
            photo.normalPhotoUrl,
            photo.bigPhotoUrl,
            photo.photographer,
            photo.photographerUrl,
            photo.width,
            photo.height
        )
    }
}