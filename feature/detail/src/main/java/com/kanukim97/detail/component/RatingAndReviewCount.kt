package com.kanukim97.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kanukim97.designsystem.theme.EatTypography

@Composable
fun RatingAndReviewCount(
    rating: () -> String,
    reviewsCount: () -> Int?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.StarRate,
            contentDescription = "star",
            tint = Color(0xFFF5C518)
        )
        Text(
            text = rating.invoke(),
            style = EatTypography.labelLarge
        )

        if (reviewsCount.invoke() != null) {
            Text(
                text = "(${reviewsCount.invoke()} Reviews)",
                color = Color.Gray,
                style = EatTypography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}