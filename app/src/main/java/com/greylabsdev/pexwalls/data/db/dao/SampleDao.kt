package com.greylabsdev.pexwalls.data.db.dao

import androidx.room.*
import com.greylabsdev.pexwalls.data.dto.SampleDto

private const val TABLE_NAME = "SampleDto"

@Dao
interface SampleDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): List<SampleDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: SampleDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: List<SampleDto>)

    @Delete
    fun delete(item: SampleDto)

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()

}