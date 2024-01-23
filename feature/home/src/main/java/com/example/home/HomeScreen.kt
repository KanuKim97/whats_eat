package com.example.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.Typography
import com.example.home.component.HomeBanner
import com.example.home.component.HomeItemGrid
import com.example.ui.preview.ComponentPreview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun HomeRoute(
    scaffoldPaddingValues: PaddingValues,
    navigateToDetail: (String) -> Unit
) {
    val context = LocalContext.current
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val homeViewModel = hiltViewModel<HomeViewModel>()
    val bannerUiState by homeViewModel.bannerUiState.collectAsStateWithLifecycle()
    val gridUiState by homeViewModel.itemGridUiState.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = locationPermission,
        block = {
            if (!locationPermission.status.isGranted) {
                locationPermission.launchPermissionRequest()
            } else {
                val locationRequest: CurrentLocationRequest = CurrentLocationRequest
                    .Builder()
                    .setDurationMillis(5000L)
                    .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                    .build()

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationClient.getCurrentLocation(
                        locationRequest,
                        object : CancellationToken() {
                            override fun onCanceledRequested(
                                p0: OnTokenCanceledListener
                            ): CancellationToken = CancellationTokenSource().token

                            override fun isCancellationRequested(): Boolean = false
                        }
                    ).addOnSuccessListener {
                        val latLng = "${it.latitude},${it.longitude}"
                        homeViewModel.getBannerUiState(latLng)
                        homeViewModel.getItemGridUiState(latLng)
                    }
                }

            }
        }
    )

    HomeScreen(
        bannerState = bannerUiState,
        itemGridState = gridUiState,
        scaffoldPaddingValues = scaffoldPaddingValues,
        itemOnClick = navigateToDetail
    )
}

@Composable
internal fun HomeScreen(
    bannerState: BannerUiState,
    itemGridState: ItemGridUiState,
    scaffoldPaddingValues: PaddingValues,
    itemOnClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(scaffoldPaddingValues),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            content =  {
                Text(
                    text = "내 주변 음식점",
                    fontWeight = FontWeight.Bold,
                    style = Typography.headlineLarge
                )
                Text(
                    text = "오늘은 여기 어떤가요?",
                    fontWeight = FontWeight.Medium,
                    style = Typography.titleSmall
                )
                HomeBanner(
                    bannerUiState = bannerState,
                    bannerOnClick = itemOnClick
                )
                Text(
                    text = "근처 맛집",
                    fontWeight = FontWeight.Medium,
                    style = Typography.titleSmall
                )
                HomeItemGrid(
                    itemGridUiState = itemGridState,
                    itemOnClick = itemOnClick
                )
            }
        )
    }
}


@ComponentPreview
@Composable
fun PreviewHomeScreen() {
    EatTheme {
        HomeScreen(
            scaffoldPaddingValues = PaddingValues(bottom = 10.dp),
            bannerState = BannerUiState.IsLoading,
            itemGridState = ItemGridUiState.IsLoading,
            itemOnClick = { _ -> },
        )
    }
}