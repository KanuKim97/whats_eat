package com.example.detail

import android.widget.Space
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
import com.example.ui.preview.ComponentPreview
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DetailScreen(modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState()

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
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
                failure = {}
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                content = {
                    Text(
                        text = "00 음식점",
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = Typography.headlineMedium
                    )
                    Text(
                        text = "00시 00구 00동 000길 00 101호",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        style = Typography.labelLarge
                    )
                    Text(
                        text = "영업 중",
                        maxLines = 1,
                        style = Typography.labelMedium
                    )
                }
            )
            Spacer(modifier = modifier.size(10.dp))
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
                    GoogleMap(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        cameraPositionState = cameraPositionState,
                    ) {

                    }
                }
            )
            EatTextButton(
                onClick = { /*TODO*/ },
                content = {
                    Text(
                        text = "컬렉션에 추가하기",
                        style = Typography.labelLarge
                    )
                }
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