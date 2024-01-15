package com.example.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Typography
import com.example.detail.DetailUiState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DetailPlaceView(
    detailUiState: DetailUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        content = {
            Text(
                text = "위치",
                fontWeight = FontWeight.SemiBold,
                style = Typography.headlineMedium
            )
            when (detailUiState) {
                is DetailUiState.IsLoading -> {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center,
                        content = {
                            CircularProgressIndicator(
                                strokeWidth = 3.dp,
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                    )
                }
                is DetailUiState.IsSuccess -> {
                    val cameraPositionState = rememberCameraPositionState()

                    GoogleMap(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        cameraPositionState = cameraPositionState,
                        content = {
                            val lat = detailUiState.info?.geometry?.location?.lat ?: 0.0
                            val lng = detailUiState.info?.geometry?.location?.lng ?: 0.0

                            Marker(state = MarkerState(position = LatLng(lat, lng)))
                        }
                    )
                }
                is DetailUiState.IsFailed -> {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center,
                        content = {
                            CircularProgressIndicator(
                                strokeWidth = 3.dp,
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                    )
                }
            }
        }
    )
}