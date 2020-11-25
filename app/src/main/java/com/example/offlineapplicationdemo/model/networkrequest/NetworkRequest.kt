package com.example.offlineapplicationdemo.model.networkrequest

import android.content.Context
import com.example.offlineapplicationdemo.BuildConfig
import com.example.offlineapplicationdemo.model.bean.UserDataResponse
import com.example.offlineapplicationdemo.utility.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkRequest {

    @GET("/api/users")
    suspend fun getUserList(@Query("page") page: Int) : Response<UserDataResponse>

    companion object {
        operator fun invoke(context: Context) : NetworkRequest = run {
            val interceptor = Interceptor {
                val isConnected = checkNetworkConnection(context)
                if(!isConnected) throw UnreachableNetworkException()

                val request = it.request()
                logDebug(request.method(), request.url().toString())

                val requestBuilder = request.newBuilder()
                    .addHeader(KEY_DEVICE_ID, "")
                    .addHeader(KEY_VERSION_CODE, "")
                    .addHeader(KEY_AUTHENTICATION, "")

                it.proceed(requestBuilder.build())
            }

            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(okHttpClient)
                .build()

            retrofit.create(NetworkRequest::class.java)
        }
    }
}