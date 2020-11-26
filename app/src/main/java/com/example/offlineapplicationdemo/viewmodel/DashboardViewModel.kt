package com.example.offlineapplicationdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.offlineapplicationdemo.model.bean.UserDataResponse
import com.example.offlineapplicationdemo.model.repo.DashboardRepo
import com.example.offlineapplicationdemo.model.repo.RepoListener
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DashboardViewModel(private val dashboardRepo: DashboardRepo) : BaseViewModel() {
    private val _userDataResponse = MutableLiveData<UserDataResponse>()
    val userDataResponse: LiveData<UserDataResponse> = _userDataResponse

    /*fun getUserListUsingCoroutine(page: Int = 1) {
        dashboardRepo.getUserListUsingCoroutine(object : RepoListener<UserDataResponse> {
            override fun onSuccess(result: UserDataResponse) {
                _userDataResponse.postValue(result)
            }

            override fun onFailed(responseCode: Int, message: String) {
                _failureResponse.postValue(Pair(responseCode, message))
            }
        })
    }*/

    fun getUserList(observer: SingleObserver<UserDataResponse>) {
        dashboardRepo.getUserList(observer)
    }
}