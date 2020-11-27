package com.example.offlineapplicationdemo.view.base.activity

import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

abstract class BaseActivity : AppCompatActivity(), DIAware {
    override val di: DI by closestDI()
}