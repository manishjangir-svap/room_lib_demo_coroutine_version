package com.example.offlineapplicationdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.offlineapplicationdemo.utility.SOMETHING_WENT_WRONG
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    protected val _failureResponse = MutableLiveData<Pair<Int, String>>()
    val failureResponse: LiveData<Pair<Int, String>> = _failureResponse
    protected val _progressStatus = MutableLiveData<Boolean>()
    val progressStatus: LiveData<Boolean> = _progressStatus

    fun <T> request(
        request: (observer: SingleObserver<T>) -> Unit,
        success: (result: T) -> Unit
    ) {
          request(object : SingleObserver<T> {
              override fun onSubscribe(d: Disposable?) {
                  _progressStatus.postValue(true)
              }

              override fun onSuccess(t: T) {
                  _progressStatus.postValue(true)
                  success(t)
              }

              override fun onError(e: Throwable?) {
                  _progressStatus.postValue(true)
                  _failureResponse.postValue(Pair(0, e?.message ?: SOMETHING_WENT_WRONG))
              }
          })
    }
}