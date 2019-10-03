package com.greylabsdev.pexwalls.presentation.const

import androidx.annotation.ColorRes
import com.greylabsdev.pexwalls.R
import java.io.Serializable

sealed class PhotoCategory(
    val name: String,
    @ColorRes val color: Int? = null
): Serializable {
    //future categories for wallpapers, using like search request to API but with name string

    //base themes
    class ABSTRACT: PhotoCategory("abstract")
    class NATURE: PhotoCategory("nature")
    class ARCHITECTURE: PhotoCategory("architecture")
    class ANIMALS: PhotoCategory("animals")
    class PORTRAITS: PhotoCategory("portraits")
    class SEA: PhotoCategory("sea")
    class NIGHT: PhotoCategory("night")

    //colors
    class COLOR_BLACK: PhotoCategory("black", R.color.colorBlack)
    class COLOR_WHITE: PhotoCategory("white", R.color.colorLight)
    class COLOR_RED: PhotoCategory("red", R.color.colorRed)
    class COLOR_GREEN: PhotoCategory("green", R.color.colorGreen)
    class COLOR_BLUE: PhotoCategory("blue", R.color.colorBlue)
    class COLOR_YELLOW: PhotoCategory("yellow", R.color.colorYellow)
    class COLOR_VIOLET: PhotoCategory("violet", R.color.colorViolet)
}