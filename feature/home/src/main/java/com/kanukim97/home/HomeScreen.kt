package com.example.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.EatTypography
import com.example.home.component.HomeBanner
import com.example.home.component.HomeItemGrid
import com.example.ui.PermissionAlertDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun HomeRoute(
    navigateToDetail: (String) -> Unit,
    getBannerUiState: (String) -> Unit,
    getItemGridUiState: (String) -> Unit,
    getMainBannerState: BannerUiState,
    getItemsState: ItemGridUiState
) {
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
    val locationLatitude = remember { mutableDoubleStateOf(0.0) }
    val locationLongitude = remember { mutableDoubleStateOf(0.0) }

    LaunchedEffect(
        key1 = locationPermissionState,
        key2 = locationLatitude,
        key3 = locationLongitude
    ) {
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
                locationLatitude.doubleValue = location.latitude
                locationLongitude.doubleValue = location.longitude

                getBannerUiState("${location.latitude}, ${location.longitude}")
                getItemGridUiState("${location.latitude}, ${location.longitude}")
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
        bannerState = getMainBannerState,
        itemGridState = getItemsState,
        itemOnClick = navigateToDetail,
    )
}

@Composable
internal fun HomeScreen(
    bannerState: BannerUiState,
    itemGridState: ItemGridUiState,
    itemOnClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        content =  {
            HomeBanner(
                bannerUiState = bannerState,
                bannerOnClick = itemOnClick
            )
            Text(
                text = "근처 맛집",
                modifier = modifier.padding(start = 16.dp, bottom = 16.dp),
                style = EatTypography.titleSmall
            )
            HomeItemGrid(
                itemGridUiState = itemGridState,
                itemOnClick = itemOnClick
            )
        }
    )
}

fun Context.openAppSetting() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also { startActivity(it) }
}