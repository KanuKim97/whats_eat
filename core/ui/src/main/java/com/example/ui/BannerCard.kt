package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatCard
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.theme.EatTypography
import com.example.domain.model.NearByPlaceItemModel

@Composable
fun BannerCard(
    item: NearByPlaceItemModel?,
    itemOnClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    EatCard(
        onClick = { itemOnClick(item?.placeId ?: "") },
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            content = {
                EatImageLoader(
                    imageModel = item?.placePhotoReference ?: "",
                    modifier = modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Text(
                    text = item?.placeName ?: "",
                    fontWeight = FontWeight.SemiBold,
                    style = EatTypography.titleMedium,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
            }
        )
    }
}