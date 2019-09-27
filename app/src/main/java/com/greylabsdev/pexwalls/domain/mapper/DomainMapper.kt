package com.greylabsdev.pexwalls.domain.mapper

import com.greylabsdev.pexwalls.data.dto.PhotoDto
import com.greylabsdev.pexwalls.domain.entity.PhotoEntity
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
}