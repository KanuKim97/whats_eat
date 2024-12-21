package com.kanukim97.detail

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanukim97.designsystem.component.EatCircularProgressIndicator
import com.kanukim97.designsystem.theme.EatShape
import com.kanukim97.designsystem.theme.EatTypography
import com.kanukim97.detail.component.LocationMapView
import com.kanukim97.ui.PlaceInfo
import com.kanukim97.designsystem.component.EatOutlinedTextButton
import com.kanukim97.detail.state.DetailUiState
import com.kanukim97.detail.state.SaveCollectionState
import com.kanukim97.ui.EatImageHorizontalPagerWithIndicator

@Composable
internal fun DetailScreenRoot(
    placeId: String,
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val detailUiState by detailViewModel.detailUiState.collectAsStateWithLifecycle()
    val saveCollectionUiState by detailViewModel.saveCollectionState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        detailViewModel.getDetailUiState(placeId)
    }

    DetailScreen(
        detailUiState = detailUiState,
        saveCollectionUiState = saveCollectionUiState,
        saveCollection = detailViewModel::savePlaceInfo,
        modifier = modifier
    )
}

@Composable
internal fun DetailScreen(
    detailUiState: DetailUiState,
    saveCollectionUiState: SaveCollectionState,
    saveCollection: (String, String, String, String) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    val localContext = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (detailUiState is DetailUiState.Success) {
                    Modifier
                        .padding(10.dp)
                        .verticalScroll(scrollState)
                } else {
                    Modifier
                }
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        when (detailUiState) {
            is DetailUiState.Loading -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    EatCircularProgressIndicator()
                }
            }
            is DetailUiState.Success -> {
                EatImageHorizontalPagerWithIndicator(
                    imageUrlList = detailUiState.info.placeImgUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Text(
                    text = detailUiState.info.placeName,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = EatTypography.titleLarge
                )

                PlaceInfo(
                    address = detailUiState.info.placeAddress,
                    openTime = detailUiState.info.isPlaceOpenNow,
                    phoneNumber = detailUiState.info.placePhoneNumber,
                    ratingNumber = detailUiState.info.placeRating,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LocationMapView(
                    placeName = detailUiState.info.placeName,
                    placeLatitude = detailUiState.info.placeLatitude,
                    placeLongitude = detailUiState.info.placeLongitude
                )
                Spacer(modifier = modifier.height(50.dp))

                when (saveCollectionUiState) {
                    SaveCollectionState.Init -> {
                        EatOutlinedTextButton(
                            onClick = {
                                saveCollection(
                                    detailUiState.info.placeId,
                                    detailUiState.info.placeName,
                                    detailUiState.info.placeImgUrl.first(),
                                    "${detailUiState.info.placeLatitude}, ${detailUiState.info.placeLongitude}"
                                )
                            },
                            modifier = modifier.fillMaxWidth(),
                            shape = EatShape.extraLarge,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.inverseSurface
                            ),
                            content = { Text(text = "저장하기", style = EatTypography.labelLarge) }
                        )
                    }
                    SaveCollectionState.Loading -> { EatCircularProgressIndicator() }
                    SaveCollectionState.Success -> {
                        Toast.makeText(
                            localContext,
                            "저장에 성공하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is SaveCollectionState.Failed -> {
                        Toast.makeText(
                            localContext,
                            "저장에 실패하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            is DetailUiState.Failed -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "로딩에 실패하였습니다.",
                        style = EatTypography.labelLarge
                    )
                }
            }
        }
    }
}