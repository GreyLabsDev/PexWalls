package com.greylabsdev.pexwalls.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity

private const val TABLE_NAME = "PhotoDbEntity"

@Dao
interface PhotoDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): List<PhotoDbEntity>

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllPhotos(): List<PhotoDbEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE id LIKE :photoId")
    fun getById(photoId: Int): List<PhotoDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PhotoDbEntity)

    @Delete
    fun delete(item: PhotoDbEntity)
}
