package com.example.offlineapplicationdemo.model.repo

import android.content.Context
import com.example.offlineapplicationdemo.model.bean.UserData
import com.example.offlineapplicationdemo.model.bean.UserDataResponse
import com.example.offlineapplicationdemo.model.networkrequest.NetworkGateway
import com.example.offlineapplicationdemo.model.room.database.AppRoomDatabase
import com.example.offlineapplicationdemo.utility.SOMETHING_WENT_WRONG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance
import java.lang.Exception

class DashboardRepo(private val context: Context, private val networkGateway: NetworkGateway): DIAware {
    override val di: DI by closestDI(context)

    @Suppress("UNCHECKED_CAST")
    fun <T> getUserList(repoListener: RepoListener<T>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userDao = AppRoomDatabase.invoke(context).userDao()

                // Get data from Room
                val userDataList = userDao.getUserList()
                if(userDataList.isNotEmpty()) {
                    val useDataResponse: UserDataResponse by instance()
                    useDataResponse.data = ArrayList()
                    useDataResponse.data?.addAll(userDataList)
                    repoListener.onSuccess(useDataResponse as T)
                }

                // Get data from server
                networkGateway.request(
                    request = { it.getUserList(1) },
                    failure = {code, message -> repoListener.onFailed(code, message) },
                    success = {
                        it.data?.let { users ->
                            if(users.isNotEmpty()) {
                                userDao.deleteAllUsers()
                                userDao.insertAll(users.toList())
                                repoListener.onSuccess(it as T)
                            }
                        }
                    }
                )
            } catch (exception: Exception) {
                repoListener.onFailed(0, exception.message ?: SOMETHING_WENT_WRONG)
            }
        }
    }
}