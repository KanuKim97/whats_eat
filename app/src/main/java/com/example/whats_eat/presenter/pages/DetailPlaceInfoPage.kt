package com.example.whats_eat.presenter.pages

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.model.collection.Collection
import com.example.whats_eat.R
import com.example.whats_eat.presenter.items.detailPlaceInfo.DetailPlaceSection
import com.example.ui.preview.DevicePreview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DetailPlaceInfoPage(
    placeID: String?,
    detailPlaceInfo: Collection?,
    getDetailPlaceInfo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val localContext = LocalContext.current

    LaunchedEffect(Unit) {
        if (placeID != null) {
            getDetailPlaceInfo(placeID)
        } else {
            Toast.makeText(
                localContext,
                "정보 불러오기를 실패했습니다.\n 재시도 해주세요",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val isOpenNow = if (detailPlaceInfo?.isOpenNow == true) {
        stringResource(id = R.string.isOpenNow)
    } else {
        stringResource(id = R.string.isCloseNow)
    }

    val placeLatLng = if (detailPlaceInfo != null) {
        LatLng(detailPlaceInfo.lat!!, detailPlaceInfo.lng!!)
    } else {
        LatLng(0.0, 0.0)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(placeLatLng, 10f)
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState(), enabled = true),
        content = {
            DetailPlaceSection(
                modifier = modifier,
                placeName = detailPlaceInfo?.name.toString(),
                placeAddress = detailPlaceInfo?.formattedAddress.toString(),
                placePhotoRef = detailPlaceInfo?.photoRef.toString(),
                openNow = isOpenNow,
                cameraPositionState = cameraPositionState,
                mapsContent = { Marker(state = MarkerState(placeLatLng)) },
                addCollectionOnClick = { /*TODO*/ }
            )
        }
    )

}

@com.example.ui.preview.DevicePreview
@Composable
fun PreviewDetailPlaceInfoPage() {
    DetailPlaceInfoPage(
        placeID = null,
        detailPlaceInfo = null,
        getDetailPlaceInfo = { _ -> }
    )
}