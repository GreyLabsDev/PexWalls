package com.greylabsdev.pexwalls.domain.entity

data class PhotoFavoriteEntity(
    val id: Int,
    val normalPhotoUrl: String,
    val bigPhotoUrl: String,
    val photographer: String,
    val photographerUrl: String,
    val width: Int,
    val height: Int
)