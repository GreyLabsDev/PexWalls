package com.greylabsdev.pexwalls.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    protected val _progressState = MutableStateFlow<ProgressState?>(null)
    val progressState: StateFlow<ProgressState?> = _progressState.asStateFlow()
}
