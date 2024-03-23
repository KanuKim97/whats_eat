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
fun PlaceAddressRow(
    address: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = 3.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Icon(
                imageVector = EatIcons.locationOnBordered,
                contentDescription = "Location",
                tint = Gray
            )
            Text(
                text = address,
                style = EatTypography.labelLarge
            )
        }
    )
}

@ComponentPreview
@Composable
fun PreviewPlaceAddressRow() {
    EatTheme {
        PlaceAddressRow(address = "성북구 종암로 8")
    }
}