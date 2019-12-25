package com.greylabsdev.pexwalls.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SampleDto(
    @PrimaryKey
    @ColumnInfo
    var id: String,
    @ColumnInfo
    val data: String
)
