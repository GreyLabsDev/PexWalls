package com.greylabsdev.pexwalls.domain.usecase

import com.greylabsdev.pexwalls.domain.entity.PhotoFavoriteEntity
import com.greylabsdev.pexwalls.domain.mapper.DomainMapper
import com.greylabsdev.pexwalls.domain.repository.IRepository
import io.reactivex.Completable
import io.reactivex.Single

class PhotoFavoritesUseCase (
    private val repository: IRepository
) {

    fun getFavoritePhotos(): Single<List<PhotoFavoriteEntity>> {
        return repository.getAllFavoritePhotos().map {photos ->
            photos.map { DomainMapper.mapToPhotoFavoriteEntity(it) }
        }
    }

    fun addPhotoToFavorites(photo: PhotoFavoriteEntity): Completable {
        return repository.addPhotoToFavorites(DomainMapper.mapToDbEntity(photo))
    }

    fun removePhotoFromFavorites(photo: PhotoFavoriteEntity): Completable {
        return repository.removePhotoFromFavorites(DomainMapper.mapToDbEntity(photo))
    }

    fun checkIfPhotoInFavorites(id: Int): Single<Boolean> {
        return repository.checkIfPhotoInFavorites(id)
    }

}