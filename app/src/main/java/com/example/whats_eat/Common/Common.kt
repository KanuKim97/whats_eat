package com.example.whats_eat.Common

import com.example.whats_eat.remote.IGoogleAPIService
import com.example.whats_eat.remote.RetrofitClient

object Common {

    private val googleApiUrl = "https://maps.googleapis.com/"

    val googleApiService : IGoogleAPIService
        get() = RetrofitClient.getClient(googleApiUrl).create(IGoogleAPIService::class.java)

}