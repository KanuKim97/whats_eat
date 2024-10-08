package com.example.network.util

import com.example.network.constant.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

fun OkHttpClient.Builder.addDefaultTimeOut(): OkHttpClient.Builder =
    this.callTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)
        .readTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)
        .writeTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)
        .connectTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)

internal val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}