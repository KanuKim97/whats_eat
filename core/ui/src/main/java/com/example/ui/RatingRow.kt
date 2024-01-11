package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.Typography

@Composable
fun RatingRow(
    modifier: Modifier = Modifier,
    ratingNumber: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Icon(
                imageVector = EatIcons.starBordered,
                contentDescription = "Star Rate"
            )
            Spacer(modifier = modifier.size(3.dp))
            Text(
                text = ratingNumber,
                fontWeight = FontWeight.Bold,
                style = Typography.labelLarge
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewRatingRow() {
    RatingRow(ratingNumber = "4.7")
}