package com.greylabsdev.pexwalls.domain.usecase

import com.greylabsdev.pexwalls.domain.entity.PhotoEntity
import com.greylabsdev.pexwalls.domain.mapper.DomainMapper
import com.greylabsdev.pexwalls.domain.repository.IRepository
import io.reactivex.Observable

class PhotoDisplayingUseCase(private val repository: IRepository) {

    fun getPhotosForCategory(
        category: String,
        pageNumber: Int,
        perPageCount: Int
    ): Observable<List<PhotoEntity>> {
        return repository.searchPhotos(category, pageNumber, perPageCount)
            .map { it.photos.map {photoDto ->  DomainMapper.mapToPhotoEntity(photoDto) } }
    }

    fun getPhotoCategoryCover(category: String): Observable<String> {
        return repository.searchPhotos(category, 1, 30)
            .map { it.photos.map {photoDto ->  DomainMapper.mapToPhotoEntity(photoDto) } }
            .map { it.random().url }
    }

    fun getCuratedPhotos(pageNumber: Int, perPageCount: Int): Observable<List<PhotoEntity>> {
        return repository.getCuratedPhotos(pageNumber, perPageCount)
            .map { it.photos.map {photoDto ->  DomainMapper.mapToPhotoEntity(photoDto) } }
    }

    fun serachPhoto(query: String,
                    pageNumber: Int,
                    perPageCount: Int
    ): Observable<List<PhotoEntity>> {
        return repository.searchPhotos(query, pageNumber, perPageCount)
            .map { it.photos.map {photoDto ->  DomainMapper.mapToPhotoEntity(photoDto) } }
    }

}