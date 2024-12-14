package com.example.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.EatShape
import com.example.designsystem.theme.EatTypography
import com.example.ui.preview.ComponentPreview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun LocationMapView(
    placeName: String,
    placeLatitude: Double,
    placeLongitude: Double,
    modifier: Modifier = Modifier
) {
    val placeLatLng = LatLng(placeLatitude, placeLongitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(placeLatLng, 16f)
    }
    val markerState = MarkerState(placeLatLng)

    Column {
        Text(
            text = "위치",
            fontWeight = FontWeight.SemiBold,
            style = EatTypography.titleMedium
        )
        Spacer(modifier = modifier.size(10.dp))
        GoogleMap(
            modifier = modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(EatShape.large),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = markerState,
                title = placeName
            )
        }
    }
}

@ComponentPreview
@Composable
fun PreviewLocationMapView() {
    LocationMapView(
        placeName = "문가네",
        placeLatitude = 0.0,
        placeLongitude = 0.0
    )
}