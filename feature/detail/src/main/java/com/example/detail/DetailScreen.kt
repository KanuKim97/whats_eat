package com.example.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.component.EatTextButton
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.Typography
import com.example.detail.component.DetailInfo
import com.example.detail.component.DetailPlaceView
import com.example.ui.preview.ComponentPreview
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DetailScreen(modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState()

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            EatImageLoader(
                imageModel = "",
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp),
                success = { imageState, _ ->
                    imageState.imageBitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "Image"
                        )
                    }
                },
                failure = { /*TODO*/ }
            )
            DetailInfo(
                name = "00 음식점",
                address = "00시 00구 00동 00로 0길 00 000호",
                openNow = true
            )
            Spacer(modifier = modifier.size(10.dp))
            DetailPlaceView(
                cameraPositionState = cameraPositionState,
                mapsContent = { /*TODO*/ }
            )
            EatTextButton(
                onClick = { /*TODO*/ },
                content = { Text(text = "컬렉션에 추가하기", style = Typography.labelLarge) }
            )
        }
    }
}

@ComponentPreview
@Composable
fun PreviewDetail() {
    EatTheme {
        DetailScreen()
    }
}