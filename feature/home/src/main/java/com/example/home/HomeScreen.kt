package com.example.home

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.EatLargeTopAppBar
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.EatTypography
import com.example.home.component.HomeBanner
import com.example.home.component.HomeItemGrid
import com.example.ui.preview.ComponentPreview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
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
    val lifecycleOwner = LocalLifecycleOwner.current
    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { permission ->
            if (permission) {

            } else {

            }
        }
    )

    val homeViewModel = hiltViewModel<HomeViewModel>()
    val bannerUiState by homeViewModel.bannerUiState.collectAsStateWithLifecycle()
    val gridUiState by homeViewModel.itemGridUiState.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = locationPermissionState,
        block = {
            if (!locationPermissionState.status.isGranted) {
                locationPermissionState.launchPermissionRequest()
            } else {
                locationClient.getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    CancellationTokenSource().token
                ).addOnSuccessListener { location ->
                    if (location != null) {
                        val latLng = "${location.latitude},${location.longitude}"
                        homeViewModel.getBannerUiState(latLng)
                        homeViewModel.getItemGridUiState(latLng)
                    }
                }
            }
        }
    )

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START && !locationPermissionState.status.isGranted) {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }
    )

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