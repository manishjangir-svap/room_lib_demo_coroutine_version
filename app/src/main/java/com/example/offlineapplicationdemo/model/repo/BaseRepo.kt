package com.example.offlineapplicationdemo.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseRepo {
    fun <T, D> request(
        observer: SingleObserver<T>,
        networkRequest: () -> Single<T>,
        roomRequest: () -> Single<D>,
        roomAction: (result: D) -> T,
        resetRoom: (T?) -> Unit
    ) {
        roomRequest().subscribeOn(Schedulers.io())
            .map { roomAction(it) }
            .subscribe(observer)

        networkRequest().subscribeOn(Schedulers.io())
            .map { resetRoom(it); it }
            .subscribe(object : SingleObserver<T> {
                override fun onSubscribe(d: Disposable?) {}
                override fun onSuccess(t: T?) = resetRoom(t)
                override fun onError(e: Throwable?) {}
            })
    }
}