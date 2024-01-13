package com.example.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.designsystem.theme.Typography

@Composable
fun DetailInfo(
    name: String,
    address: String,
    openNow: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = Typography.headlineMedium
        )
        Text(
            text = address,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = Typography.labelLarge
        )
        Text(
            text = if (openNow) { "영업 중" } else { "영업 종료" },
            maxLines = 1,
            style = Typography.labelMedium
        )
    }
}