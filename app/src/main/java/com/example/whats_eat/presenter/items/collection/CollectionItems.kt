package com.example.whats_eat.presenter.items.collection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whats_eat.presenter.items.common.RatingRow

@Composable
fun CollectionItems(
    modifier: Modifier,
    placeName: String,
    placeAddress: String,
    ratingNumber: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = ShapeDefaults.Medium,
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = placeName,
                modifier = modifier.padding(top = 3.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = placeAddress,
                modifier = modifier.padding(vertical = 3.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            RatingRow(
                modifier = modifier.padding(bottom = 3.dp),
                ratingNumber = ratingNumber
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCollectionItem() {
    CollectionItems(
        modifier = Modifier,
        placeName = "00상회",
        placeAddress = "00시 000구 000동 000번지 000길 00 0층 000호",
        ratingNumber = "4.7"
    )
}