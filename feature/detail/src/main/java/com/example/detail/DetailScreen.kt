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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatCircularProgressIndicator
import com.example.designsystem.component.EatTextButton
import com.example.designsystem.theme.EatShape
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.EatTypography
import com.example.detail.component.LocationMapView
import com.example.detail.component.PlaceImagePager
import com.example.ui.PlaceInfo
import com.example.ui.preview.DevicePreview

@Composable
internal fun DetailRoute(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState,
    saveCollectionUiState: SaveCollectionState,
    scrollState: ScrollState,
    saveCollection: (String, String, String, String) -> Unit
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
    saveCollection: (String, String, String, String) -> Unit,
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
                PlaceImagePager(
                    placeImgList = detailUiState.info.placeImgUrl,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(300.dp)
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

                LocationMapView(
                    placeName = detailUiState.info.placeName,
                    placeLatitude = detailUiState.info.placeLatitude,
                    placeLongitude = detailUiState.info.placeLongitude
                )
                Spacer(modifier = modifier.height(50.dp))

                when (saveCollectionUiState) {
                    SaveCollectionState.Init -> {
                        EatTextButton(
                            onClick = {
                                saveCollection(
                                    detailUiState.info.placeId,
                                    detailUiState.info.placeName,
                                    "${detailUiState.info.placeLatitude}, ${detailUiState.info.placeLongitude}",
                                    detailUiState.info.placeImgUrl.first()
                                )
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
            detailUiState = DetailUiState.IsFailed,
            scrollState = rememberScrollState(),
            saveCollectionUiState = SaveCollectionState.Init,
            saveCollection = { _, _, _, _ -> }
        )
    }
}