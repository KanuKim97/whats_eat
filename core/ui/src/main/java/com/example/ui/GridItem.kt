package com.example.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.theme.EatTypography
import com.example.domain.model.NearByPlaceItemModel

@Composable
fun GridItem(
    item: NearByPlaceItemModel,
    itemOnClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .clickable(onClick = { itemOnClick(item.placeId) }),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            content = {
                EatImageLoader(
                    imageModel = item.placePhotoReference,
                    modifier = modifier.size(160.dp)
                )
                Text(
                    text = item.placeName,
                    modifier = modifier
                        .width(160.dp)
                        .wrapContentHeight(),
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = EatTypography.labelLarge
                )
            }
        )
    }
}