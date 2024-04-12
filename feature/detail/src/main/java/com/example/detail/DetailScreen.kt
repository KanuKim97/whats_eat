package com.example.detail

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatCircularProgressIndicator
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.component.EatTextButton
import com.example.designsystem.theme.EatShape
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.EatTypography
import com.example.domain.entity.DetailedModel
import com.example.model.collection.CollectionModel
import com.example.ui.PlaceInfo
import com.example.ui.preview.DevicePreview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
internal fun DetailRoute(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState,
    saveCollectionUiState: SaveCollectionState,
    scrollState: ScrollState,
    saveCollection: (CollectionModel) -> Unit
) {
    DetailScreen(
        detailUiState = detailUiState,
        saveCollectionUiState = saveCollectionUiState,
        scrollState = scrollState,
        saveCollection = saveCollection,
        modifier = modifier
    )
}

@Composable
internal fun DetailScreen(
    detailUiState: DetailUiState,
    saveCollectionUiState: SaveCollectionState,
    scrollState: ScrollState,
    saveCollection: (CollectionModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val localContext = LocalContext.current

    when (detailUiState) {
        is DetailUiState.IsLoading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                content = { EatCircularProgressIndicator() }
            )
        }
        is DetailUiState.IsSuccess -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                val lat = detailUiState.info.placeLatitude
                val lng = detailUiState.info.placeLongitude
                val placeLatLng = LatLng(lat, lng)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(placeLatLng, 16f)
                }
                EatImageLoader(
                    imageModel = detailUiState.info.placeImgUrl,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Text(
                    text = detailUiState.info.placeName,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = EatTypography.titleLarge
                )
                Spacer(modifier = modifier.size(3.dp))
                PlaceInfo(
                    address = detailUiState.info.placeAddress,
                    openTime = detailUiState.info.isPlaceOpenNow,
                    phoneNumber = detailUiState.info.placePhoneNumber,
                    ratingNumber = detailUiState.info.placeRating
                )
                Spacer(modifier = modifier.size(9.dp))
                Text(
                    text = "위치",
                    fontWeight = FontWeight.SemiBold,
                    style = EatTypography.titleMedium
                )
                GoogleMap(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(EatShape.large),
                    cameraPositionState = cameraPositionState,
                    content = {
                        Marker(
                            state = MarkerState(position = placeLatLng),
                            title = detailUiState.info.placeName
                        )
                    }
                )
                Spacer(modifier = modifier.height(50.dp))

                when (saveCollectionUiState) {
                    SaveCollectionState.Init -> {
                        EatTextButton(
                            onClick = {
                                val collectionModel = CollectionModel(
                                    id = detailUiState.info.placeId,
                                    name = detailUiState.info.placeName,
                                    latLng = "${detailUiState.info.placeLatitude}, ${detailUiState.info.placeLongitude}",
                                    imgUrl = detailUiState.info.placeImgUrl
                                )

                                saveCollection(collectionModel)
                            },
                            modifier = modifier.fillMaxWidth(),
                            shape = EatShape.extraLarge,
                            content = { Text(text = "저장하기", style = EatTypography.labelLarge) }
                        )
                    }
                    SaveCollectionState.IsLoading -> { EatCircularProgressIndicator() }
                    SaveCollectionState.IsSuccess -> {
                        Toast.makeText(
                            localContext,
                            "저장에 성공하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is SaveCollectionState.IsFailed -> {
                        Toast.makeText(
                            localContext,
                            "저장에 실패하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        is DetailUiState.IsFailed -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { Text(text = "로딩에 실패하였습니다.", style = EatTypography.labelLarge) }
            )
        }
    }
}


@DevicePreview
@Composable
fun PreviewDetailScreenWhenSuccess() {
    EatTheme {
        DetailScreen(
            detailUiState = DetailUiState
                .IsSuccess(
                    DetailedModel(
                        placeId = "",
                        placeName = "종암에너비스 종암지점",
                        placeAddress = "성북구 종암동 10-15",
                        placeImgUrl = "",
                        placeLatitude = 0.0,
                        placeLongitude = 0.0,
                        isPlaceOpenNow = false,
                        placeRating = "4.7",
                        placePhoneNumber = "02-3333-4444",
                    )
                ),
            scrollState = rememberScrollState(),
            saveCollectionUiState = SaveCollectionState.Init,
            saveCollection = { _ -> }
        )
    }
}

@DevicePreview
@Composable
fun PreviewDetailScreenWhenFailed() {
    EatTheme {
        DetailScreen(
            detailUiState = DetailUiState.IsFailed,
            scrollState = rememberScrollState(),
            saveCollectionUiState = SaveCollectionState.Init,
            saveCollection = { _ -> }
        )
    }
}