package com.example.whats_eat.data.remote

import com.example.whats_eat.data.common.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofit : Retrofit? = null

    val PlaceApiService: IGoogleAPIService
        get() = getClient()
            .create(IGoogleAPIService::class.java)

    fun getClient() : Retrofit{
        if(retrofit ==null){
            val httpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(Constant.IPlaceAPIUri)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}