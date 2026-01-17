package com.kanukim97.home.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kanukim97.ui.dialog.PermissionAlertDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun HomeRoute() {
    val context = LocalContext.current

    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = { isGranted ->
            if (isGranted) {
                Toast.makeText(
                    context,
                    "위치 사용 권한이 동의되었습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationPermissionShowRational = remember { mutableStateOf(false) }

    var latLng by remember { mutableStateOf(Pair(0.0, 0.0)) }

    LaunchedEffect(locationPermissionState, latLng) {
        if (!locationPermissionState.status.isGranted) {
            if (locationPermissionState.status.shouldShowRationale) {
                locationPermissionShowRational.value = true
            } else {
                locationPermissionState.launchPermissionRequest()
            }
        } else {
            locationClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { location ->
                latLng = Pair(location.latitude, location.longitude)
            }
        }
    }

    if (locationPermissionShowRational.value) {
        PermissionAlertDialog(
            onDismiss = { context.openAppSetting() },
            onConfirmClick = {
                locationPermissionShowRational.value = false
                locationPermissionState.launchPermissionRequest()
            },
            onDismissClick = {
                locationPermissionShowRational.value = false
            }
        )
    }



    HomeScreen(
        latLng = latLng
    )
}

@Composable
internal fun HomeScreen(
    latLng: Pair<Double, Double>
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latLng.first, latLng.second), 17f)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    compassEnabled = false
                )
            ) {

            }

            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.TopCenter)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {

                }
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.End
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.padding(vertical = 8.dp).size(48.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MyLocation,
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                }

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                }
            }

        }
    }
}

fun Context.openAppSetting() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also { startActivity(it) }
}