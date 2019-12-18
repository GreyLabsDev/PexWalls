package com.greylabsdev.pexwalls.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoDbEntity(
    @PrimaryKey
    @ColumnInfo
    val id: Int,
    @ColumnInfo
    val normalPhotoUrl: String,
    @ColumnInfo
    val bigPhotoUrl: String,
    @ColumnInfo
    val byScreenResolution: String,
    @ColumnInfo
    val photographer: String,
    @ColumnInfo
    val photographerUrl: String,
    @ColumnInfo
    val width: Int,
    @ColumnInfo
    val height: Int
)
