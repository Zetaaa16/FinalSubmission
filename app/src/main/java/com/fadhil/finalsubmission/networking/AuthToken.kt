package com.fadhil.finalsubmission.networking

import android.content.Context
import com.fadhil.finalsubmission.storage.pref.PreferenceDataSource
import okhttp3.Interceptor
import okhttp3.Response


class AuthToken(context: Context) : Interceptor {
    private val sessionManager = PreferenceDataSource.invoke(context)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}