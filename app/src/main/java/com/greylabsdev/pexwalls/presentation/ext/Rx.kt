package com.greylabsdev.pexwalls.presentation.ext

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.Subject

fun <T: Any> Subject<T>.shedulersSubscribe(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T: Any> Observable<T>.shedulersSubscribe(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T: Any>Observable<T>.mainThreadObserve(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}