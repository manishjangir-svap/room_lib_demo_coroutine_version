package com.example.offlineapplicationdemo.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

@Suppress("DEPRECATION")
fun checkNetworkConnection(context: Context) : Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        //Return true only if working internet connection is available.
        //If connected to network but internet is not working return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo ?: return false
        return activeNetworkInfo.isConnectedOrConnecting
    }
}

fun Int.toPx(context: Context) = context.resources.displayMetrics.density * this