package com.greylabsdev.pexwalls.presentation.view.navigation

class ActiveButtonsManager {

    private var activeButton: NavigationButton? = null
    private val buttons = mutableListOf<NavigationButton>()
    private val inactiveButtons: List<NavigationButton>
        get() = buttons.filter { it != activeButton }

    var onActiveButtonChangedAction: ((activeButton: NavigationButton, inactiveButtons: List<NavigationButton>) -> Unit)? = null

    fun toggleButtonToActive(button: NavigationButton) {
        if (activeButton != button) {
            activeButton = button
            activeButton?.let { onActiveButtonChangedAction?.invoke(it, inactiveButtons) }
        }
    }

    fun addButton(button: NavigationButton) {
        buttons.add(button)
    }
}