package com.example.offlineapplicationdemo.application

import android.app.Application
import com.example.offlineapplicationdemo.model.bean.UserDataResponse
import com.example.offlineapplicationdemo.model.networkrequest.NetworkGateway
import com.example.offlineapplicationdemo.model.networkrequest.NetworkRequest
import com.example.offlineapplicationdemo.model.repo.DashboardRepo
import com.example.offlineapplicationdemo.viewmodel.DashboardViewModelFactory
import org.kodein.di.*

class OfflineApplicationDemo : Application(), DIAware {
    override val di by DI.lazy {
        bind() from factory { UserDataResponse() }

        bind() from singleton { NetworkRequest(this@OfflineApplicationDemo) }

        bind() from singleton { NetworkGateway(instance()) }

        bind() from singleton { DashboardRepo(this@OfflineApplicationDemo, instance()) }

        bind() from singleton { DashboardViewModelFactory(instance()) }
    }
}