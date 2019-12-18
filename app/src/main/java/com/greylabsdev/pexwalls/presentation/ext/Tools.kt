package com.greylabsdev.pexwalls.presentation.ext

/**
 * Returning random element from input variables
 *
 * val randomLetter = randomFrom("a","b","c","d","e","f","g")
 *
 * @property variables
 */
fun <T> randomFrom(vararg variables: T): T {
    return variables.random()
}

/**
 * Multiple let. Allows you to use .let for few variables combinig its in vararg
 * and if all of them is not null returning list of checked non-null values with same order.
 *
 * multiLet(a,b,c) { values ->
 *     print("a is not null and = ${values[0]}")
 *     print("b is not null and = ${values[1]}")
 *     print("c is not null and = ${values[2]}")
 * }
 *
 * @property variables - vararg of input nullable variables
 */
inline fun <T : Any, R : Any> multiLet(vararg variables: T?, out: (List<T>) -> R?): R? {
    return if (variables.all { it != null }) out(listOfNotNull(*variables)) else null
}
