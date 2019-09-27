package com.greylabsdev.pexwalls.data.dto

import com.google.gson.annotations.SerializedName

data class PhotoSrcDto(
    @SerializedName("landscape")
    val landscape: String,
    @SerializedName("large")
    val large: String,
    @SerializedName("large2x")
    val large2x: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("original")
    val original: String,
    @SerializedName("portrait")
    val portrait: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("tiny")
    val tiny: String
)