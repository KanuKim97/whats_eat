package com.example.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.Typography
import com.example.ui.BannerCard
import com.example.ui.GridItem
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
internal fun HomeRoute(modifier: Modifier = Modifier) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val bannerState by homeViewModel.bannerState.collectAsState()
    val itemGridState by homeViewModel.gridItemState.collectAsState()

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
            is PermissionStatus.Denied -> {  }
        }
    }

    HomeScreen(
        bannerState = bannerState,
        itemGridState = itemGridState,
        itemOnClick = {},
        modifier = modifier
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    bannerState: BannerUiState,
    itemGridState: ItemGridUiState,
    itemOnClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { 5 })
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(10.dp),
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
            when (bannerState) {
                is BannerUiState.IsLoading -> { CircularProgressIndicator() }
                is BannerUiState.IsSuccess -> {
                    HorizontalPager(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        state = pagerState,
                        pageSpacing = 20.dp,
                        userScrollEnabled = true,
                        pageContent = { index ->
                            BannerCard(
                                bannerTitle = bannerState.banner?.get(index)?.name.toString(),
                                bannerImgUri = bannerState.banner?.get(index)?.photoRef.toString(),
                                bannerOnClick = {  }
                            )
                        }
                    )
                }
                is BannerUiState.IsFailed -> { /* TODO() */ }
            }

            Spacer(modifier = modifier.size(10.dp))
            Text(
                text = "근처 맛집",
                fontWeight = FontWeight.Medium,
                style = Typography.titleSmall
            )
            Spacer(modifier = modifier.size(10.dp))
            when (itemGridState) {
                is ItemGridUiState.IsLoading -> {
                    CircularProgressIndicator()
                }
                is ItemGridUiState.IsSuccess -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(count = 2),
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        content = {
                            itemGridState.item?.let { gridItemList ->
                                items(items = gridItemList) { item ->
                                    GridItem(
                                        itemTitle = item.name,
                                        itemImgUri = item.photoRef,
                                        itemOnClick = { /*TODO*/ }
                                    )
                                }
                            }
                        }
                    )
                }
                is ItemGridUiState.IsFailed -> { /* TODO() */ }
            }

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