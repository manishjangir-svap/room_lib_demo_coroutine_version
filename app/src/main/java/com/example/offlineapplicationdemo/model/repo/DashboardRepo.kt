package com.example.offlineapplicationdemo.model.repo

import android.content.Context
import com.example.offlineapplicationdemo.application.OfflineApplicationDemo
import com.example.offlineapplicationdemo.model.bean.UserDataResponse
import com.example.offlineapplicationdemo.model.networkrequest.NetworkGateway
import com.example.offlineapplicationdemo.model.networkrequest.NetworkRequest
import com.example.offlineapplicationdemo.model.room.database.AppRoomDatabase
import com.example.offlineapplicationdemo.utility.SOMETHING_WENT_WRONG
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class DashboardRepo(
    context: Context,
    private val networkGateway: NetworkGateway,
    private val networkRequest: NetworkRequest,
    private val appRoomDatabase: AppRoomDatabase
) : BaseRepo(), DIAware {
    override val di: DI by lazy { (context as OfflineApplicationDemo).di }
    private val userDao = appRoomDatabase.userDao()

    @Suppress("UNCHECKED_CAST")
    fun getUserList(observer: SingleObserver<UserDataResponse>) {
        request(
            observer,
            networkRequest = { networkRequest.getUserList(1) },
            roomRequest = { userDao.getUserList() },
            roomAction = {
                val useDataResponse: UserDataResponse by instance()
                if (it.isNotEmpty()) {
                    useDataResponse.data = ArrayList()
                    useDataResponse.data?.addAll(it)
                }
                useDataResponse
            },
            resetRoom = {
                it?.data?.let { it2 ->
                    if(it2.size > 0) {
                        userDao.deleteAllUsers()
                        userDao.insertAll(it2.toList())
                    }
                }
            }
        )
    }

    /*@Suppress("UNCHECKED_CAST")
    fun <T> getUserListUsingCoroutine(repoListener: RepoListener<T>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userDao = appRoomDatabase.userDao()

                // Get data from Room
                val userDataList = userDao.getUserListCoroutine()
                if (userDataList.isNotEmpty()) {
                    val useDataResponse: UserDataResponse by instance()
                    useDataResponse.data = ArrayList()
                    useDataResponse.data?.addAll(userDataList)
                    repoListener.onSuccess(useDataResponse as T)
                }

                // Get data from server
                networkGateway.requestUsingSuspend(
                    request = { it.getUserListUsingSuspend(1) },
                    failure = { code, message -> repoListener.onFailed(code, message) },
                    success = {
                        it.data?.let { users ->
                            if (users.isNotEmpty()) {
                                userDao.deleteAllUsers()
                                userDao.insertAll(users.toList())
                                repoListener.onSuccess(it as T)
                            }
                        }
                    }
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
                repoListener.onFailed(0, exception.message ?: SOMETHING_WENT_WRONG)
            }
        }
    }*/
}