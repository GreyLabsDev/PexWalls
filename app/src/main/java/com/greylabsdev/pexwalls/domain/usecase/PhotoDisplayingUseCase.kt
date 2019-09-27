package com.greylabsdev.pexwalls.domain.usecase

import com.greylabsdev.pexwalls.data.network.PexelsApi
import com.greylabsdev.pexwalls.domain.entity.PhotoEntity
import com.greylabsdev.pexwalls.domain.mapper.DomainMapper
import io.reactivex.Observable

class PhotoDisplayingUseCase(private val api: PexelsApi) {

    fun getPhotosForCategory(
        category: String,
        pageNumber: Int,
        perPageCount: Int
    ): Observable<List<PhotoEntity>> {
        return api.searchPhotoByQuery(category, pageNumber, perPageCount)
            .map { it.photos.map {photoDto ->  DomainMapper.mapToPhotoEntity(photoDto) } }
    }

}