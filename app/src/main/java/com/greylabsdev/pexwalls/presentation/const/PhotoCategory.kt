package com.greylabsdev.pexwalls.presentation.const

import java.io.Serializable

sealed class PhotoCategory(val name: String): Serializable {
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
    class COLOR_BLACK: PhotoCategory("black")
    class COLOR_WHITE: PhotoCategory("white")
    class COLOR_RED: PhotoCategory("red")
    class COLOR_GREEN: PhotoCategory("green")
    class COLOR_BLUE: PhotoCategory("blue")
    class COLOR_YELLOW: PhotoCategory("yellow")
    class COLOR_VIOLET: PhotoCategory("violet")
}