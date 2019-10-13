package com.greylabsdev.pexwalls.presentation.ext

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.Subject

fun Completable.schedulersSubscribe(): Completable {
    return this.subscribeOn(Schedulers.io())
}

fun Completable.mainThreadObserve(): Completable {
    return this.observeOn(AndroidSchedulers.mainThread())
}

fun <T: Any> Subject<T>.schedulersSubscribe(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T: Any> Observable<T>.schedulersSubscribe(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T: Any>Observable<T>.mainThreadObserve(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}

fun <T: Any> Single<T>.schedulersSubscribe(): Single<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T: Any> Single<T>.mainThreadObserve(): Single<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}