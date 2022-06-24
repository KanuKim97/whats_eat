package com.example.whats_eat.data.common

import com.example.whats_eat.data.model.commonModel.Results
import com.example.whats_eat.data.remote.IGoogleAPIService
import com.example.whats_eat.data.remote.RetrofitClient

object Common {

    private const val googleApiUrl = "https://maps.googleapis.com/"

    var currentPlace : Results? = null

    val googleApiService : IGoogleAPIService
        get() = RetrofitClient
            .getClient(googleApiUrl)
            .create(IGoogleAPIService::class.java)

}