package com.greylabsdev.pexwalls.presentation.ext

/**
 * Returnng random element from input variables
 *
 * val randomLetter = randomFrom("a","b","c","d","e","f","g")
 *
 * @property variables
 */
fun <T> randomFrom(vararg variables: T): T {
    return variables.random()
}
