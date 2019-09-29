package com.greylabsdev.pexwalls.presentation.ext

fun Int.isOdd(): Boolean {
    return (this % 2 != 0)
}
 fun Int.isEven(): Boolean {
     return (this % 2 == 0)
 }