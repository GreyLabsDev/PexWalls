package com.greylabsdev.pexwalls.presentation.ext

/**
 * JUST FOR FUN!
 * Because why not?
 *
 * replace for stadard if (condition) { action } constructon to more beautiful:
 *
 * condition.then {
 *     action
 * }
 *
 * @property action - action that you want to do if condition is true
 *
 */
fun Boolean.then(action: () -> Unit): Boolean {
    if (this) action.invoke()
    return this
}

/**
 * JUST FOR FUN!
 * Because why not?
 *
 * replace for stadard if (!condition) { action } constructon to more beautiful:
 *
 * condition.orNot {
 *     action
 * }
 *
 * can be used with previous .then extension in chain like this:
 *
 * condition.then {
 *    action
 * }.orNot {
 *    another action
 * }
 *
 * @property action - action that you want to do if condition is false
 *
 */
fun Boolean.orNot(action: () -> Unit): Boolean {
    if (!this) action.invoke()
    return this
}
