package com.example.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.EatCircularProgressIndicator
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.component.EatTextButton
import com.example.designsystem.theme.Typography
import com.example.model.collection.Collection
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
internal fun DetailRoute(modifier: Modifier = Modifier) {
    val detailViewModel = hiltViewModel<DetailViewModel>()
    val detailUiState by detailViewModel.detailUiState.collectAsStateWithLifecycle()
    val collectionUiState by detailViewModel.saveCollectionState.collectAsStateWithLifecycle()

    DetailScreen(
        detailUiState = detailUiState,
        collectionUiState = collectionUiState,
        addOnCollection = detailViewModel::saveCollection,
        modifier = modifier
    )
}

@Composable
internal fun DetailScreen(
    detailUiState: DetailUiState,
    collectionUiState: SaveCollectionState,
    addOnCollection: (Collection) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            when (detailUiState) {
                is DetailUiState.IsLoading -> {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                        content = { EatCircularProgressIndicator() }
                    )
                }
                is DetailUiState.IsSuccess -> {
                    val lat = detailUiState.info?.geometry?.location?.lat ?: 0.0
                    val lng = detailUiState.info?.geometry?.location?.lng ?: 0.0
                    val placeLatLng = LatLng(lat, lng)
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(placeLatLng, 16f)
                    }

                    EatImageLoader(
                        imageModel = detailUiState.info?.let { photo ->
                            photo.photos[0].photo_reference
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    Text(
                        text = detailUiState.info?.name ?: "불러오지 못했습니다.",
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = Typography.headlineMedium
                    )
                    Text(
                        text = if (detailUiState.info?.opening_hours?.open_now == true) {
                            "영업 중"
                        } else {
                            "영업 종료"
                        },
                        maxLines = 1,
                        style = Typography.labelMedium
                    )
                    Text(
                        text = "위치",
                        fontWeight = FontWeight.SemiBold,
                        style = Typography.headlineMedium
                    )
                    GoogleMap(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        cameraPositionState = cameraPositionState,
                        content = {
                            Marker(
                                state = MarkerState(position = placeLatLng),
                                title = detailUiState.info?.name ?: ""
                            )
                        }
                    )
                    Spacer(modifier = modifier.size(20.dp))
                    EatTextButton(
                        onClick = {
                            val collection = detailUiState.info?.let {
                                Collection(
                                    id = it.place_id,
                                    name = it.name,
                                    latLng = "${lat},${lng}",
                                    imgUrl = it.photos[0].photo_reference
                                )
                            }
                            addOnCollection(checkNotNull(collection))
                        },
                        modifier = modifier.fillMaxWidth(),
                        content = { Text(text = "컬렉션에 추가하기", style = Typography.labelLarge) }
                    )
                }
                is DetailUiState.IsFailed -> {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                        content = { Text(text = "로딩에 실패하였습니다.", style = Typography.labelLarge) }
                    )
                }
            }
        }
    }
}