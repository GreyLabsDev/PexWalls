package com.greylabsdev.pexwalls.presentation.view.navigation

class ActiveButtonsManager() {

    private var _activeButton: NavigationButton? = null
    val activeButton: NavigationButton?
        get() = _activeButton

    private val buttons = mutableListOf<NavigationButton>()

    var onActiveButtonChangedAction: ((activeButton: NavigationButton, inactiveButtons: List<NavigationButton>) -> Unit)? = null

    private val inactiveButtons: List<NavigationButton>
        get() = buttons.filter { it != activeButton }

    fun toggleButtonToActive(button: NavigationButton) {
        if (activeButton != button) {
            _activeButton = button
            activeButton?.let { onActiveButtonChangedAction?.invoke(it, inactiveButtons) }
        }
    }

    fun addButton(button: NavigationButton) {
        buttons.add(button)
    }
}