package com.greylabsdev.pexwalls.presentation.base

sealed class ProgressState {
    class DONE: ProgressState()
    class LOADING: ProgressState()
    class ERROR(errorMessage: String): ProgressState()
    class INITIAL(): ProgressState()
    class EMPTY(): ProgressState()
}