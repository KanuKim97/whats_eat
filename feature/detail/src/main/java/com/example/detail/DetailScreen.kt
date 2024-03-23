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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.EatCenterAlignedAppBar
import com.example.designsystem.component.EatCircularProgressIndicator
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.icons.EatIcons
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
    navigationIconOnClick: () -> Unit,
    navigateToCollectionScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val detailViewModel = hiltViewModel<DetailViewModel>()
    val detailUiState by detailViewModel.detailUiState.collectAsStateWithLifecycle()
    val saveCollectionUiState by detailViewModel.saveCollectionState.collectAsStateWithLifecycle()

    DetailScreen(
        detailUiState = detailUiState,
        saveCollectionUiState = saveCollectionUiState,
        saveCollection = detailViewModel::saveCollection,
        navigationIconOnClick = navigationIconOnClick,
        navigateToCollectionScreen = navigateToCollectionScreen,
        modifier = modifier
    )
}

@Composable
internal fun DetailScreen(
    detailUiState: DetailUiState,
    saveCollectionUiState: SaveCollectionState,
    saveCollection: (CollectionModel) -> Unit,
    navigationIconOnClick: () -> Unit,
    navigateToCollectionScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            Scaffold(
                modifier = modifier.fillMaxSize(),
                topBar = {
                    EatCenterAlignedAppBar(
                        navigationIcon = EatIcons.arrowBackOutlined,
                        navigationIconOnClick = navigationIconOnClick,
                        actions = {
                            IconButton(
                                onClick = {
                                    val collection = CollectionModel(
                                        id = detailUiState.info.placeId,
                                        name = detailUiState.info.placeName,
                                        latLng = "${detailUiState.info.placeLatitude}, ${detailUiState.info.placeLongitude}",
                                        imgUrl = detailUiState.info.placeImgUrl
                                    )
                                    saveCollection(collection)

                                    when (saveCollectionUiState) {
                                        is SaveCollectionState.IsLoading -> {  }
                                        is SaveCollectionState.IsSuccess -> {  }
                                        is SaveCollectionState.IsFailed -> {  }
                                    }
                                },
                                content = {
                                    Icon(
                                        imageVector = EatIcons.plusOutlined,
                                        contentDescription = "Add"
                                    )
                                }
                            )
                            IconButton(
                                onClick = navigateToCollectionScreen,
                                content = {
                                    Icon(
                                        imageVector = EatIcons.CollectionOutlined,
                                        contentDescription = "Collection"
                                    )
                                }
                            )
                        }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(10.dp),
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
fun PreviewDetailScreen() {
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
            saveCollectionUiState = SaveCollectionState.IsLoading,
            saveCollection = { /*TODO*/ },
            navigationIconOnClick = { /*TODO*/ },
            navigateToCollectionScreen = { /*TODO*/ })
    }
}