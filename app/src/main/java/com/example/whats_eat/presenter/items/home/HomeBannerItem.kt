package com.example.whats_eat.presenter.items.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whats_eat.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeBannerItem(
    bannerTitle: String,
    bannerImageUri: String,
    bannerOnClickEvent: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable(onClick = bannerOnClickEvent)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            GlideImage(
                imageModel = { bannerImageUri },
                modifier = modifier
                    .fillMaxWidth()
                    .height(250.dp),
                success = { imageState, _ ->
                    imageState.imageBitmap?.let { image ->
                        Image(
                            bitmap = image,
                            contentDescription = "Image Viewer",
                            contentScale = ContentScale.Crop
                        )
                    }
                },
                failure = {
                    Image(
                        painter = painterResource(id = R.drawable.cancel_24),
                        contentDescription = "",
                        modifier = modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                    )
                }
            )
            Text(
                text = bannerTitle,
                modifier = modifier.padding(start = 15.dp, top = 10.dp),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBannerItem() {
    HomeBannerItem(
        bannerTitle = "Title",
        bannerImageUri = "",
        bannerOnClickEvent = {}
    )
}