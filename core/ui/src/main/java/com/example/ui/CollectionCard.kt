package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatCard
import com.example.designsystem.theme.Typography

@Composable
fun CollectionCard(
    onClick: () -> Unit,
    modifier: Modifier,
    placeName: String,
    placeAddress: String,
    ratingNumber: String
) {
    EatCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        content = {
            Column(
                modifier = modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = placeName,
                    style = Typography.headlineSmall
                )
                Text(
                    text = placeAddress,
                    modifier = modifier.padding(vertical = 5.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = Typography.labelMedium
                )
                RatingRow(ratingNumber = ratingNumber)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCollectionCard() {
    CollectionCard(
        onClick = {  },
        modifier = Modifier,
        placeName = "00상회",
        placeAddress = "00시 000구 000동 000번지 000길 00 0층 000호",
        ratingNumber = "4.7"
    )
}