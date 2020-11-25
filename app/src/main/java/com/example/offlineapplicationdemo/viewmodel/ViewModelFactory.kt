package com.example.offlineapplicationdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.offlineapplicationdemo.model.repo.DashboardRepo

@Suppress("UNCHECKED_CAST")
class DashboardViewModelFactory(private val dashboardRepo: DashboardRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DashboardViewModel(dashboardRepo) as T
}