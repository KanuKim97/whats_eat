package com.example.whats_eat.presenter.items.detailPlaceInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whats_eat.R
import com.example.whats_eat.presenter.items.common.TextButton
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DetailPlaceSection(
    modifier: Modifier,
    placeName: String,
    placeAddress: String,
    openNow: String,
    cameraPositionState: CameraPositionState,
    mapsContent: @Composable @GoogleMapComposable () -> Unit,
    addCollectionOnClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            content = { /* TODO */ }
        )
        PlaceInfoTxtView(
            modifier = modifier,
            placeName = placeName,
            placeAddress = placeAddress,
            openNow = openNow
        )
        PlaceLocationView(
            modifier = modifier.padding(bottom = 10.dp),
            cameraPositionState = cameraPositionState,
            mapsContent = mapsContent
        )
        TextButton(
            onClick = addCollectionOnClick,
            modifier = modifier.wrapContentHeight(),
            btnTextContent = stringResource(id = R.string.AddCollection_Btn),
            btnColors = Color.White,
            btnElevation = 10.dp,
            textColor = Color.Black,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailPlaceSection() {
    DetailPlaceSection(
        modifier = Modifier,
        placeName = "",
        placeAddress = "",
        openNow = "",
        cameraPositionState = rememberCameraPositionState(),
        mapsContent = { /*TODO*/ },
        addCollectionOnClick = { /*TODO*/ }
    )
}