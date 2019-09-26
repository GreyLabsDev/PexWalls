package com.greylabsdev.pexwalls.presentation.const

sealed class ImageCategory(themeName: String) {
    //future categories for wallpapers, using like search request to API but with themeName string

    //base themes
    class NATURE: ImageCategory("")
    class ARCHITECTURE: ImageCategory("")
    class ANIMALS: ImageCategory("")
    class PORTRAITS: ImageCategory("")
    class SEA: ImageCategory("")
    class NIGHT: ImageCategory("")

    //colors
    class COLOR_BLACK: ImageCategory("")
    class COLOR_WHITE: ImageCategory("")
    class COLOR_RED: ImageCategory("")
    class COLOR_GREEN: ImageCategory("")
    class COLOR_BLUE: ImageCategory("")
    class COLOR_YELLOW: ImageCategory("")
    class COLOR_VIOLET: ImageCategory("")
}