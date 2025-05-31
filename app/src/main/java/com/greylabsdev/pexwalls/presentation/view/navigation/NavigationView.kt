package com.greylabsdev.pexwalls.presentation.view.navigation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.databinding.ViewNavigationBinding

class NavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val activeScreensManager = ActiveButtonsManager()
    private val binding: ViewNavigationBinding

    init {
        View.inflate(context, R.layout.view_navigation, this)
        binding = ViewNavigationBinding.bind(this)
    }

    fun setupButtons(vararg buttons: NavigationButton) {
        buttons.forEach { button ->
            button.layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT,
                1.0f
            )
            binding.root.addView(button)
            activeScreensManager.addButton(button)
            button.setOnClickListener {
                activeScreensManager.toggleButtonToActive(button)
                button.onClickAction?.invoke()
            }
        }
        activeScreensManager.onActiveButtonChangedAction = { activeButton, inactiveButtons ->
            activeButton.swapOut()
            inactiveButtons.forEach { it.swapIn() }
        }
        activeScreensManager.toggleButtonToActive(buttons.first())
    }
}
