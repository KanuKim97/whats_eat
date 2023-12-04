package com.example.whats_eat.presenter.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whats_eat.common.Constant
import com.example.whats_eat.presenter.items.home.HomeBannerPager
import com.example.whats_eat.presenter.items.home.HomeBannerRow
import com.example.whats_eat.presenter.items.home.HomeGridList
import com.example.whats_eat.presenter.items.home.HomeGridTitleRow
import com.example.whats_eat.util.MainBannerItems
import com.example.whats_eat.util.MainGridItems
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlin.text.StringBuilder

@Suppress("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomePage(
    mainBannerItems: ArrayList<MainBannerItems>,
    mainGridItems: ArrayList<MainGridItems>,
    getMainBannerItems: (String) -> Unit,
    getMainGridItems: (String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    LaunchedEffect(Unit) {
        when(permissionState.status) {
            is PermissionStatus.Granted -> {
                val locationRequest = CurrentLocationRequest.Builder()
                    .setDurationMillis(Constant.MAPS_INTERVAL_MILLIS)
                    .setPriority(Constant.MAPS_PRIORITY_HIGH)
                    .build()

                fusedLocationClient.getCurrentLocation(
                    locationRequest,
                    object : CancellationToken() {
                        override fun onCanceledRequested(
                            tokenCanceledListener: OnTokenCanceledListener
                        ): CancellationToken = CancellationTokenSource().token

                        override fun isCancellationRequested(): Boolean = false
                    }
                ).addOnSuccessListener { location ->
                    val locationLatLng =
                        StringBuilder("${location.latitude},${location.longitude}").toString()

                    getMainBannerItems(locationLatLng)
                    getMainGridItems(locationLatLng)
                }
            }
            is PermissionStatus.Denied -> {
                //TODO(Permission Denied)
            }
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HomeBannerRow()
            HomeBannerPager(
                navController = navController,
                mainBannerItems = mainBannerItems
            )
            HomeGridTitleRow()
            HomeGridList(gridItems = mainGridItems)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    HomePage(
        mainBannerItems = arrayListOf(),
        mainGridItems = arrayListOf(),
        getMainBannerItems = { _ -> },
        getMainGridItems = { _ -> },
        navController = rememberNavController()
    )
}