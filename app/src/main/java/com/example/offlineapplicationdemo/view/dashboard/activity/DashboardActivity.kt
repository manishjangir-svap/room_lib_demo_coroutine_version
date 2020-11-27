package com.example.offlineapplicationdemo.view.dashboard.activity

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.offlineapplicationdemo.R
import com.example.offlineapplicationdemo.databinding.ActivityDashboardBinding
import com.example.offlineapplicationdemo.utility.logInfo
import com.example.offlineapplicationdemo.view.base.activity.BaseActivity
import com.example.offlineapplicationdemo.viewmodel.DashboardViewModel
import com.example.offlineapplicationdemo.viewmodel.DashboardViewModelFactory
import org.kodein.di.instance

class DashboardActivity : BaseActivity() {
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
        observeData()
    }

    private fun requestData() {
        if(::dashboardViewModel.isInitialized) dashboardViewModel.getUserList()
    }

    private fun observeData() {
        if(::dashboardViewModel.isInitialized) {
            dashboardViewModel.userDataResponse.observe(this, {
                viewDataBinding.data = it
            })

            dashboardViewModel.failureResponse.observe(this, {
                Toast.makeText(this, it.second, Toast.LENGTH_SHORT).show()
            })

            dashboardViewModel.progressStatus.observe(this, {
                logInfo("Progress Status", it.toString())
            })
        }
    }
}