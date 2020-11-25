package com.example.offlineapplicationdemo.utility

import android.util.Log
import com.example.offlineapplicationdemo.BuildConfig

fun logDebug(key: String, value: String) {
    if(BuildConfig.LOG) Log.d(key, value)
}

fun logError(key: String, value: String) {
    if(BuildConfig.LOG) Log.e(key, value)
}

fun logInfo(key: String, value: String) {
    if(BuildConfig.LOG) Log.i(key, value)
}