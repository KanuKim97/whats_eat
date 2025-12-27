package com.kanukim97.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.kanukim97.designsystem.component.EatCircularProgressIndicator
import com.kanukim97.designsystem.component.EatImageLoader
import com.kanukim97.designsystem.component.EatTextButton
import com.kanukim97.designsystem.theme.EatShape
import com.kanukim97.designsystem.theme.EatTypography
import com.kanukim97.ui.PlaceInfo
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.kanukim97.detail.action.DetailUiAction
import com.kanukim97.detail.state.DetailUiState

@Composable
internal fun DetailRoute(viewModel: DetailViewModel = hiltViewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.viewModelEvent.collect { event ->
                when (event) {
                    DetailViewModel.Event.NavigateBack -> {

                    }
                    is DetailViewModel.Event.ShowDialog -> {

                    }
                    is DetailViewModel.Event.ShowSnackBar -> {

                    }
                    is DetailViewModel.Event.ShowToast -> {

                    }
                }
            }
        }
    }

    DetailScreen(
        uiState = uiState,
        onAction = viewModel::handleAction
    )
}

@Composable
internal fun DetailScreen(
    uiState: DetailUiState,
    onAction: (DetailUiAction) -> Unit
) {
    when (uiState) {
        DetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                EatCircularProgressIndicator()
            }
        }
        is DetailUiState.Success -> {
            val scrollState = rememberScrollState()
            val latLng = remember(uiState) {
                LatLng(uiState.info.latitude, uiState.info.longitude)
            }
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(latLng, 16f)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                EatImageLoader(
                    imageModel = uiState.info.imageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Text(
                    text = uiState.info.name,
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = EatTypography.titleLarge
                )
                PlaceInfo(
                    address = uiState.info.address,
                    openTime = uiState.info.isOpened,
                    phoneNumber = uiState.info.phoneNumber,
                    ratingNumber = uiState.info.rating,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "위치",
                    fontWeight = FontWeight.SemiBold,
                    style = EatTypography.titleMedium
                )
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(EatShape.large),
                    cameraPositionState = cameraPositionState,
                    content = {
                        Marker(
                            state = MarkerState(position = latLng),
                            title = uiState.info.name
                        )
                    }
                )
                Spacer(modifier = Modifier.height(50.dp))
                EatTextButton(
                    onClick = {
                        onAction(
                            DetailUiAction.OnLikeBtnClick(
                                id = uiState.info.id,
                                name = uiState.info.name,
                                latLng = "${latLng.latitude}, ${latLng.longitude}",
                                imageUrl = uiState.info.imageUrl
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = EatShape.extraLarge,
                    content = { Text(text = "저장하기", style = EatTypography.labelLarge) }
                )
            }
        }
        DetailUiState.Failed -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "로딩에 실패하였습니다.",
                    style = EatTypography.labelLarge
                )
            }
        }
    }
}