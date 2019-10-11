package com.greylabsdev.pexwalls.domain.usecase

import com.greylabsdev.pexwalls.domain.entity.PhotoEntity
import com.greylabsdev.pexwalls.domain.mapper.DomainMapper
import com.greylabsdev.pexwalls.domain.repository.IRepository
import com.greylabsdev.pexwalls.domain.tools.PhotoUrlGenerator
import com.greylabsdev.pexwalls.domain.tools.ResolutionManager
import com.greylabsdev.pexwalls.presentation.const.PhotoCategory
import com.greylabsdev.pexwalls.presentation.model.CategoryModel
import io.reactivex.Observable
import io.reactivex.Single

class PhotoDisplayingUseCase(
    private val repository: IRepository,
    private val resolutionManager: ResolutionManager
) {

    private val photoUrlGenerator = PhotoUrlGenerator()

    fun getPhotosForCategory(
        category: String,
        pageNumber: Int,
        perPageCount: Int
    ): Observable<List<PhotoEntity>> {
        return repository.searchPhotos(category, pageNumber, perPageCount)
            .map {
                it.photos.map {photoDto ->
                    val byScreenResolution = photoUrlGenerator.generateUrl(photoDto.src.large, resolutionManager.screenResolution)
                    DomainMapper.mapToPhotoEntity(photoDto, byScreenResolution)
                }
            }
    }

    fun getPhotoCategoryCover(category: PhotoCategory): Observable<CategoryModel> {
        return repository.searchPhotos(category.name, 1, 30)
            .map { CategoryModel(category, it.photos.random().src.large) }
    }

    fun getCuratedPhotos(pageNumber: Int, perPageCount: Int): Observable<List<PhotoEntity>> {
        return repository.getCuratedPhotos(pageNumber, perPageCount)
            .map {
                it.photos.map {photoDto ->
                    val byScreenResolution = photoUrlGenerator.generateUrl(photoDto.src.large, resolutionManager.screenResolution)
                    DomainMapper.mapToPhotoEntity(photoDto, byScreenResolution)
                }
            }
    }

    fun searchPhoto(query: String,
                    pageNumber: Int,
                    perPageCount: Int
    ): Observable<List<PhotoEntity>> {
        return repository.searchPhotos(query, pageNumber, perPageCount)
            .map {
                it.photos.map {photoDto ->
                    val byScreenResolution = photoUrlGenerator.generateUrl(photoDto.src.large, resolutionManager.screenResolution)
                    DomainMapper.mapToPhotoEntity(photoDto, byScreenResolution)
                }
            }
    }

}