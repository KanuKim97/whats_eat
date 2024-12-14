package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.EatTypography
import com.example.designsystem.theme.Gray
import com.example.ui.preview.ComponentPreview

@Composable
fun PlaceRatingRow(
    ratingNumber: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = 3.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Icon(
                imageVector = EatIcons.starBordered,
                contentDescription = "Star Rate",
                tint = Gray
            )
            Text(
                text = ratingNumber,
                fontWeight = FontWeight.Bold,
                style = EatTypography.labelLarge
            )
        }
    )
}

@ComponentPreview
@Composable
fun PreviewRatingRow() {
    PlaceRatingRow(
        ratingNumber = "4.7",
        modifier = Modifier.fillMaxWidth()
    )
}