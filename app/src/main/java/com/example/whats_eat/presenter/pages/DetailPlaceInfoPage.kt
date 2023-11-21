package com.example.whats_eat.presenter.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.whats_eat.presenter.items.detailPlaceInfo.DetailPlaceSection
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DetailPlaceInfoPage(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        content = {
            DetailPlaceSection(
                modifier = modifier,
                placeName = "00상회",
                placeAddress = "00시 00구 00동 000번지 00길 00",
                openNow = "영업 중",
                cameraPositionState = rememberCameraPositionState(),
                mapsContent = { /*TODO*/ },
                addCollectionOnClick = { /*TODO*/ }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailPlaceInfoPage() {
    DetailPlaceInfoPage()
}