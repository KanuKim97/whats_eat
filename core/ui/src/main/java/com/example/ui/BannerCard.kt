package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatCard
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.theme.EatTypography
import com.example.domain.entity.BannerItemsModel

@Composable
fun BannerCard(
    banner: BannerItemsModel?,
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
            verticalArrangement = Arrangement.Center,
            content = {
                EatImageLoader(
                    imageModel = banner?.photoRef ?: "",
                    modifier = modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Text(
                    text = banner?.name ?: "",
                    fontWeight = FontWeight.SemiBold,
                    style = EatTypography.titleMedium
                )
            }
        )
    }
}