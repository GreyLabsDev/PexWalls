package com.greylabsdev.pexwalls.presentation.base

import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel(), LifecycleObserver {

    private val disposables = CompositeDisposable()
    private var _progressState: MutableLiveData<ProgressState> = MutableLiveData()

    val progressState: LiveData<ProgressState> = _progressState

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun clearDisposables() {
        disposables.clear()
    }

}