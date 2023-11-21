package com.example.whats_eat.presenter.items.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RatingRow(
    modifier: Modifier,
    ratingNumber: Double
) {
    Row(
        modifier = modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Icon(imageVector = Icons.Default.StarRate, contentDescription = "")
            Spacer(modifier = modifier.size(3.dp))
            Text(text = "$ratingNumber")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewRatingRow() {
    RatingRow(modifier = Modifier, ratingNumber = 4.7)
}