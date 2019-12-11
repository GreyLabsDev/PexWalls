package com.greylabsdev.pexwalls.domain.repository

import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import com.greylabsdev.pexwalls.data.dto.SearchResultDto
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IRepository  {

    suspend fun getCuratedPhotos(page: Int, perPage: Int): SearchResultDto?

    fun addPhotoToFavorites(photoEntity: PhotoDbEntity): Completable
    fun removePhotoFromFavorites(photoEntity: PhotoDbEntity): Completable
    fun removePhotoFromFavoritesById(id: Int): Completable
    fun checkIfPhotoInFavorites(id: Int): Single<Boolean>
    fun getPhotoById(id: Int): Single<PhotoDbEntity>
    suspend fun getAllFavoritePhotos(): List<PhotoDbEntity>

    suspend fun searchPhotos(query: String, page: Int, perPage: Int): SearchResultDto?
}