package com.example.whats_eat.common

import com.example.whats_eat.model.Results
import com.example.whats_eat.remote.IGoogleAPIService
import com.example.whats_eat.remote.RetrofitClient

object Common {

    private const val googleApiUrl = "https://maps.googleapis.com/"

    var currentPlace : Results? = null

    val googleApiService : IGoogleAPIService
        get() = RetrofitClient.getClient(googleApiUrl).create(IGoogleAPIService::class.java)

}