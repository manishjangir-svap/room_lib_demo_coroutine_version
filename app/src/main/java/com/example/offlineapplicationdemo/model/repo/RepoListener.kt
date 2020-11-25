package com.example.offlineapplicationdemo.model.repo

interface RepoListener<T> {
    fun onSuccess(result: T)
    fun onFailed(responseCode: Int, message: String) = run { }
}