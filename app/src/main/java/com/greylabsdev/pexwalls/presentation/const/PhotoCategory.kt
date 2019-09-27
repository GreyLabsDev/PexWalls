package com.greylabsdev.pexwalls.presentation.const

sealed class PhotoCategory(val name: String) {
    //future categories for wallpapers, using like search request to API but with name string

    //base themes
    class ABSTRACT: PhotoCategory("")
    class NATURE: PhotoCategory("")
    class ARCHITECTURE: PhotoCategory("")
    class ANIMALS: PhotoCategory("")
    class PORTRAITS: PhotoCategory("")
    class SEA: PhotoCategory("")
    class NIGHT: PhotoCategory("")

    //colors
    class COLOR_BLACK: PhotoCategory("")
    class COLOR_WHITE: PhotoCategory("")
    class COLOR_RED: PhotoCategory("")
    class COLOR_GREEN: PhotoCategory("")
    class COLOR_BLUE: PhotoCategory("")
    class COLOR_YELLOW: PhotoCategory("")
    class COLOR_VIOLET: PhotoCategory("")
}