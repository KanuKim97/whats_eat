package com.example.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.createSavedStateHandle
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.Typography
import com.example.home.component.HomeBanner
import com.example.home.component.HomeItemGrid
import com.example.ui.preview.ComponentPreview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun HomeRoute(
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val context = LocalContext.current
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    LaunchedEffect(permissionState) {
        when (permissionState.status) {
            is PermissionStatus.Granted -> {
                val locationRequest = CurrentLocationRequest
                    .Builder()
                    .setDurationMillis(5000L)
                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
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
                    val locationLatLng = StringBuilder(
                        "${location.latitude},${location.longitude}"
                    ).toString()

                    homeViewModel.updateLatLng(locationLatLng)
                }
            }
            is PermissionStatus.Denied -> { /* TODO */ }
        }
    }

    val bannerState by homeViewModel.bannerState.collectAsState()
    val itemGridState by homeViewModel.gridItemState.collectAsState()

    HomeScreen(
        bannerState = bannerState,
        itemGridState = itemGridState,
        itemOnClick = navigateToDetail,
        modifier = modifier
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    bannerState: BannerUiState,
    itemGridState: ItemGridUiState,
    itemOnClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "내 주변 음식점",
                modifier = modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                style = Typography.headlineLarge
            )
            Text(
                text = "오늘은 여기 어떤가요?",
                modifier = modifier.fillMaxWidth(),
                fontWeight = FontWeight.Medium,
                style = Typography.titleSmall
            )
            Spacer(modifier = modifier.size(10.dp))
            HomeBanner(
                bannerUiState = bannerState,
                bannerOnClick = itemOnClick
            )
            Spacer(modifier = modifier.size(10.dp))
            Text(
                text = "근처 맛집",
                fontWeight = FontWeight.Medium,
                style = Typography.titleSmall
            )
            Spacer(modifier = modifier.size(10.dp))
            HomeItemGrid(
                itemGridUiState = itemGridState,
                itemOnClick = itemOnClick
            )
        }
    }
}

@ComponentPreview
@Composable
fun PreviewHomeScreen() {
    EatTheme {
        HomeScreen(
            bannerState = BannerUiState.IsSuccess(listOf()),
            itemGridState = ItemGridUiState.IsSuccess(listOf()),
            itemOnClick = { _ -> },
            modifier = Modifier
        )
    }
}