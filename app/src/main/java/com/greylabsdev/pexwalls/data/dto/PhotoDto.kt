package com.greylabsdev.pexwalls.data.dto

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("photographer")
    val photographer: String?,
    @SerializedName("photographer_id")
    val photographerId: Int?,
    @SerializedName("photographer_url")
    val photographerUrl: String?,
    @SerializedName("src")
    val src: PhotoSrcDto,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int
)