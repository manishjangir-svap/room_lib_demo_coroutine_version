package com.example.offlineapplicationdemo.model.bean

import com.example.offlineapplicationdemo.utility.SOMETHING_WENT_WRONG

abstract class BaseBean {
    // for test purpose only
    val status: Boolean = true
    val responseCode: Int = 500
    val message: String = SOMETHING_WENT_WRONG
}