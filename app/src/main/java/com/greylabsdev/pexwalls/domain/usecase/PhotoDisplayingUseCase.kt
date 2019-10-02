package com.greylabsdev.pexwalls.domain.usecase

import com.greylabsdev.pexwalls.domain.entity.PhotoEntity
import com.greylabsdev.pexwalls.domain.mapper.DomainMapper
import com.greylabsdev.pexwalls.domain.repository.IRepository
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.model.CategoryModel
import io.reactivex.Observable
import io.reactivex.Single

class PhotoDisplayingUseCase(private val repository: IRepository) {

    fun getPhotosForCategory(
        category: String,
        pageNumber: Int,
        perPageCount: Int
    ): Observable<List<PhotoEntity>> {
        return repository.searchPhotos(category, pageNumber, perPageCount)
            .map { it.photos.map {photoDto ->  DomainMapper.mapToPhotoEntity(photoDto) } }
    }

    fun getPhotoCategoryCover(category: PhotoCategory): Observable<CategoryModel> {
        return repository.searchPhotos(category.name, 1, 30)
            .map { it.photos.map {photoDto ->  DomainMapper.mapToPhotoEntity(photoDto) } }
            .map { CategoryModel(category, it.random().src.large) }
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