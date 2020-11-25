package com.example.offlineapplicationdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected val _failureResponse = MutableLiveData<Pair<Int, String>>()
    val failureResponse: LiveData<Pair<Int, String>> = _failureResponse
}