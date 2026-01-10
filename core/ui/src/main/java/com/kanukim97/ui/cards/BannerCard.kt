package com.kanukim97.ui.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kanukim97.designsystem.component.EatCard
import com.kanukim97.designsystem.component.EatImageLoader
import com.kanukim97.designsystem.theme.EatTypography

@Composable
fun BannerCard(
    id: String,
    name: String,
    imageUrl: String,
    bannerOnClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    EatCard(
        onClick = { bannerOnClick(id) },
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
                    imageModel = imageUrl,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Text(
                    text = name,
                    fontWeight = FontWeight.SemiBold,
                    style = EatTypography.titleMedium,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
            }
        )
    }
}