package com.example.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatCard
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.theme.EatShape
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.Typography
import com.example.model.home.BannerItems
import com.example.ui.preview.ComponentPreview

@Composable
fun BannerCard(
    banner: BannerItems?,
    bannerOnClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    EatCard(
        onClick = { bannerOnClick(banner?.placeID ?: "") },
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            EatImageLoader(
                imageModel = banner?.photoRef ?: "",
                modifier = modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(EatShape.large),
                success = { imageState, _ ->
                    imageState.imageBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Image"
                        )
                    }
                },
                failure = {
                    Text(
                        text = "이미지를 불러오지 못했습니다.",
                        style = Typography.labelLarge
                    )
                }
            )
            Text(
                text = banner?.name ?: "",
                fontWeight = FontWeight.SemiBold,
                style = Typography.titleMedium
            )
        }
    }
}