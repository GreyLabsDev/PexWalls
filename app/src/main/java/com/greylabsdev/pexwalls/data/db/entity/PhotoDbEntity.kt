package com.greylabsdev.pexwalls.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoDbEntity(
    @PrimaryKey
    @ColumnInfo
    val id: String
)