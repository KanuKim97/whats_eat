package com.example.whats_eat.presenter.items.detailPlaceInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlaceInfoTxtView(
    modifier: Modifier,
    placeName: String,
    placeAddress: String,
    openNow: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = placeName,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = placeAddress,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            minLines = 1
        )
        Text(
            text = openNow,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlaceInfoTxtView() {
    PlaceInfoTxtView(
        modifier = Modifier,
        placeName = "00상회 성신 여대 점",
        placeAddress = "서울특별시 00구 00동 000번 0층",
        openNow = "영업 중"
    )
}