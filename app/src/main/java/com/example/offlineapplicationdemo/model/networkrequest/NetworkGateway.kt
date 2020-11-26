package com.example.offlineapplicationdemo.model.networkrequest

import com.example.offlineapplicationdemo.model.bean.BaseBean
import com.example.offlineapplicationdemo.utility.SOMETHING_WENT_WRONG
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class NetworkGateway(private val networkRequest: NetworkRequest) {

    @Suppress("UNCHECKED_CAST")
    suspend fun <T> requestUsingSuspend(
        request : suspend (networkRequest: NetworkRequest) -> Response<T>,
        success : (result: T) -> Unit = {},
        failure : (statusCode: Int, message: String) -> Unit = {_,_ -> },
    ) {
        val response = request(networkRequest).body()
        if(response != null && response is BaseBean) {
            val responseBean = response as BaseBean
            if(response.status) success(responseBean as T)
            else failure(responseBean.responseCode, responseBean.message)
        } else failure(0, SOMETHING_WENT_WRONG)
    }
}