package com.greylabsdev.pexwalls.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class PhotoSrcDto(
    @ColumnInfo
    @SerializedName("landscape")
    val landscape: String,
    @ColumnInfo
    @SerializedName("large")
    val large: String,
    @ColumnInfo
    @SerializedName("large2x")
    val large2x: String,
    @ColumnInfo
    @SerializedName("medium")
    val medium: String,
    @PrimaryKey
    @ColumnInfo
    @SerializedName("original")
    val original: String,
    @ColumnInfo
    @SerializedName("portrait")
    val portrait: String,
    @ColumnInfo
    @SerializedName("small")
    val small: String,
    @ColumnInfo
    @SerializedName("tiny")
    val tiny: String
)
