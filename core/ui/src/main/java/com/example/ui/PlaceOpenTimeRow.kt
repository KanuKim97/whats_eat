package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.EatTypography
import com.example.designsystem.theme.Gray
import com.example.ui.preview.ComponentPreview

@Composable
fun PlaceOpenTimeRow(
    openNow: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = 3.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Icon(
                imageVector = EatIcons.accessTimeBordered,
                contentDescription = "Access Time",
                tint = Gray
            )
            Text(
                text = if (openNow) {
                    "영업 중"
                } else {
                    "영업 종료"      
                },
                style = EatTypography.labelLarge
            )
        }
    )
}

@ComponentPreview
@Composable
fun PreviewPlaceOpenTimeRow() {
    EatTheme {
        PlaceOpenTimeRow(openNow = false)
    }
}