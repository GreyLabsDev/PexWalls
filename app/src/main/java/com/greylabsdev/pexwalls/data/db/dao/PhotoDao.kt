package com.greylabsdev.pexwalls.data.db.dao

import androidx.room.*
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

private const val TABLE_NAME = "PhotoDbEntity"

@Dao
interface PhotoDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): Observable<List<PhotoDbEntity>>

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllPhotos(): List<PhotoDbEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE id LIKE :photoId")
    fun getById(photoId: Int): Single<List<PhotoDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PhotoDbEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: List<PhotoDbEntity>): Completable

    @Delete
    fun delete(item: PhotoDbEntity): Completable

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    fun deleteById(id: Int): Completable

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll(): Completable

}