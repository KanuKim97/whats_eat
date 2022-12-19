package com.example.whats_eat.viewModel.fragment

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.remote.model.errorModel.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class MapsViewModel(private val appRepo: AppRepository): ViewModel() {

    init {

    }

    private fun getNearByPlace(curLatLng: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = appRepo.getNearby(
                latLng = curLatLng,
                radius = Constant.Location_Radius,
                type = Constant.Location_Type,
                Api_key = BuildConfig.GOOGLE_API_KEY
            )

            when (response.code()) {
                200 -> {
                    try {
                        val currentPlace = response.body()?.results
                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    }
                }

                400 -> {
                    val errorJsonObject: JSONObject
                    val requestErrorBody: ErrorResponse

                    try {
                        errorJsonObject = JSONObject(response.errorBody()!!.string())
                        val responseCode = errorJsonObject.getString("status")
                        val responseMsg = errorJsonObject.getString("error_message")
                        requestErrorBody = ErrorResponse(responseCode, responseMsg)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        }

    }

}