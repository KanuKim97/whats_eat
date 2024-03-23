package com.example.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.EatLargeTopAppBar
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.EatTypography
import com.example.home.component.HomeBanner
import com.example.home.component.HomeItemGrid
import com.example.ui.PermissionAlertDialog
import com.example.ui.preview.ComponentPreview
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
    navigateToCollection: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    val context = LocalContext.current
    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val homeViewModel = hiltViewModel<HomeViewModel>()
    val bannerUiState by homeViewModel.bannerUiState.collectAsStateWithLifecycle()
    val gridUiState by homeViewModel.itemGridUiState.collectAsStateWithLifecycle()

    val locationPermissionShowRational = remember { mutableStateOf(false) }
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = { isGranted ->
            if (isGranted) {
                locationClient.getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    CancellationTokenSource().token
                ).addOnSuccessListener { location ->
                    location?.let {
                        val latLng = "${it.latitude},${it.longitude}"
                        homeViewModel.getBannerUiState(latLng)
                        homeViewModel.getItemGridUiState(latLng)
                    }
                }
            }
        }
    )

    LaunchedEffect(
        key1 = locationPermissionState,
        block = {
            if (!locationPermissionState.status.isGranted) {
                if (locationPermissionState.status.shouldShowRationale) {
                    locationPermissionShowRational.value = true
                } else {
                    locationPermissionState.launchPermissionRequest()
                }
            }
        }
    )

    if (locationPermissionShowRational.value) {
        PermissionAlertDialog(
            onDismiss = { context.openAppSetting() },
            onOkClick = {
                locationPermissionShowRational.value = false
                locationPermissionState.launchPermissionRequest()
            },
            onDismissClick = {
                locationPermissionShowRational.value = false
            }
        )
    }

    HomeScreen(
        bannerState = bannerUiState,
        itemGridState = gridUiState,
        itemOnClick = navigateToDetail,
        actionIconOnClick = navigateToCollection
    )
}

@Composable
internal fun HomeScreen(
    bannerState: BannerUiState,
    itemGridState: ItemGridUiState,
    itemOnClick: (String) -> Unit,
    actionIconOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            EatLargeTopAppBar(
                mainTitle = "내 주변 음식점",
                subTitle = "오늘은 여기 어떤가요?",
                actionIcon = EatIcons.CollectionOutlined,
                actionIconOnClick = actionIconOnClick
            )
        },
        snackbarHost = {
            SnackbarHostState()
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            content = {
                HomeBanner(bannerUiState = bannerState, bannerOnClick = itemOnClick)
                Text(
                    text = "근처 맛집",
                    modifier = modifier.padding(start = 16.dp),
                    style = EatTypography.titleSmall
                )
                HomeItemGrid(itemGridUiState = itemGridState, itemOnClick = itemOnClick)
            }
        )
    }
}

fun Context.openAppSetting() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also { startActivity(it) }
}


@ComponentPreview
@Composable
fun PreviewHomeScreen() {
    EatTheme {
        HomeScreen(
            bannerState = BannerUiState.IsLoading,
            itemGridState = ItemGridUiState.IsLoading,
            itemOnClick = { _ -> },
            actionIconOnClick = {  }
        )
    }
}