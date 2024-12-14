package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.EatTheme
import com.example.ui.preview.ComponentPreview

@Composable
fun PlaceInfo(
    address: String,
    openTime: Boolean,
    phoneNumber: String,
    ratingNumber: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        content = {
            PlaceRatingRow(ratingNumber = ratingNumber)
            PlaceAddressRow(address = address)
            PlaceOpenTimeRow(openNow = openTime)
            PlacePhoneNumberRow(phoneNumber = phoneNumber)
        }
    )
}

@ComponentPreview
@Composable
fun PreviewPlaceInfo() {
    EatTheme {
        PlaceInfo(
            address = "성북구 종암로 8",
            openTime = false,
            phoneNumber = "02-9999-0000",
            ratingNumber = "4.7"
        )
    }
}