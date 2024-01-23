package com.example.detail

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.EatCircularProgressIndicator
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.component.EatTextButton
import com.example.designsystem.theme.Typography
import com.example.model.feature.CollectionModel
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
    addOnCollection: (CollectionModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val localContext = LocalContext.current

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
                            .height(300.dp)
                    )
                    Text(
                        text = detailUiState.info.placeName,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = Typography.headlineMedium
                    )
                    Text(
                        text = if (detailUiState.info.isPlaceOpenNow) {
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
                                title = detailUiState.info.placeName
                            )
                        }
                    )
                    Spacer(modifier = modifier.size(20.dp))
                    EatTextButton(
                        onClick = {
                            val collection = CollectionModel(
                                id = detailUiState.info.placeId,
                                name = detailUiState.info.placeName,
                                latLng = "${lat},${lng}",
                                imgUrl = detailUiState.info.placeImgUrl
                            )

                            addOnCollection(collection)
                            when (collectionUiState) {
                                is SaveCollectionState.IsLoading -> {}
                                is SaveCollectionState.IsSuccess -> {
                                    Toast.makeText(
                                        localContext,
                                        "저장이 완료되었습니다.",
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