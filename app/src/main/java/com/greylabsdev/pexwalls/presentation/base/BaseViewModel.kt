package com.greylabsdev.pexwalls.presentation.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    protected var _progressState: MutableLiveData<ProgressState> = MutableLiveData()
    val progressState: LiveData<ProgressState>
        get() = _progressState
}
