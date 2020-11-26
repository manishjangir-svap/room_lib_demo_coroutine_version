package com.example.offlineapplicationdemo.view.dashboard.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.offlineapplicationdemo.R
import com.example.offlineapplicationdemo.databinding.ActivityDashboardBinding
import com.example.offlineapplicationdemo.model.bean.UserDataResponse
import com.example.offlineapplicationdemo.utility.logError
import com.example.offlineapplicationdemo.utility.logInfo
import com.example.offlineapplicationdemo.viewmodel.DashboardViewModel
import com.example.offlineapplicationdemo.viewmodel.DashboardViewModelFactory
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class DashboardActivity : AppCompatActivity(), DIAware {
    override val di: DI by closestDI()
    private val dashboardViewModelFactory: DashboardViewModelFactory by instance()

    private lateinit var viewDataBinding: ActivityDashboardBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        dashboardViewModel = ViewModelProvider(this, dashboardViewModelFactory)[DashboardViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        requestData()
    }

    private fun requestData() {
        if(::dashboardViewModel.isInitialized) dashboardViewModel.getUserList(UserListObserver(viewDataBinding))
    }

    private fun observeDataCoroutine() {
        if(::dashboardViewModel.isInitialized) {
            dashboardViewModel.userDataResponse.observe(this, {
                println("Called outer observer ******************************************************")

                viewDataBinding.data = it
            })

            dashboardViewModel.failureResponse.observe(this, {
                logError("Error: ${it.first}", it.second)
                Toast.makeText(this, it.second, Toast.LENGTH_SHORT).show()
            })
        }
    }

    class UserListObserver(private val viewDataBinding: ActivityDashboardBinding) : SingleObserver<UserDataResponse> {
        override fun onSubscribe(d: Disposable?) {}
        override fun onError(e: Throwable?) {
            e?.printStackTrace()
        }

        override fun onSuccess(t: UserDataResponse?) {
            viewDataBinding.data = t
        }
    }
}