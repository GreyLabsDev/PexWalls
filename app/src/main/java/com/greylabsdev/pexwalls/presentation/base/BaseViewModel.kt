package com.greylabsdev.pexwalls.presentation.base

import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel(), LifecycleObserver {

    protected val disposables = CompositeDisposable()

    protected var _progressState: MutableLiveData<ProgressState> = MutableLiveData()
    val progressState: LiveData<ProgressState>
        get() = _progressState

    private fun clearDisposables() {
        disposables.clear()
    }

    override fun onCleared() {
        clearDisposables()
        super.onCleared()
    }

}