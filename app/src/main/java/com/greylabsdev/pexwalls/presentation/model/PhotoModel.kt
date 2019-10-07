package com.greylabsdev.pexwalls.presentation.model

import java.io.Serializable

data class PhotoModel(
    val id: Int,
    val normalPhotoUrl: String,
    val bigPhotoUrl: String,
    val photographer: String,
    val photographerUrl: String,
    val width: Int,
    val height: Int
) : Serializable