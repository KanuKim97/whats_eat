package com.example.whats_eat

import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var locationRequest: CurrentLocationRequest
    private val myLocationManager: LocationManager by lazy {
        this.getSystemService(LOCATION_SERVICE) as LocationManager
    }
    private val myFusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController: NavHostController = rememberNavController()
            val currentLocation by remember { mutableStateOf(LatLng(0.toDouble(), 0.toDouble())) }
            WhatsEatNavHost(navController = navHostController)
        }
    }

    private fun getCurrentLatLng() {
        try {
            myFusedLocationClient.getCurrentLocation(
                locationRequest,
                object: CancellationToken() {
                    override fun onCanceledRequested(
                        canceledListener: OnTokenCanceledListener
                    ): CancellationToken = CancellationTokenSource().token

                    override fun isCancellationRequested(): Boolean = false
                }
            ).addOnCompleteListener { location ->
                val lat = location.result.latitude
                val lng = location.result.longitude


            }
        } catch (e: SecurityException) { throw e }
    }
}