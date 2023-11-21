package com.example.whats_eat.presenter.items.detailPlaceInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whats_eat.R
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun PlaceLocationView(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    mapsContent: @Composable @GoogleMapComposable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.PlaceLocation),
            modifier = modifier.padding(start = 15.dp, bottom = 10.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold
        )
        GoogleMap(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            cameraPositionState = cameraPositionState,
            content = mapsContent
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlaceLocationView() {
    PlaceLocationView(
        modifier = Modifier,
        cameraPositionState = rememberCameraPositionState(),
        mapsContent = { /*TODO*/ }
    )
}