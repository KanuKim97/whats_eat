package com.example.whats_eat.presenter.items.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whats_eat.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeGridItem(
    gridItemTitle: String,
    gridItemImgUri: String,
    gridItemOnClickEvent: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .clickable(onClick = gridItemOnClickEvent),
        contentAlignment = Alignment.TopStart,
        content = {
            Column {
                GlideImage(
                    imageModel = { gridItemImgUri },
                    modifier = modifier
                        .size(160.dp)
                        .aspectRatio(1f),
                    success = { imageState, _ ->
                        imageState.imageBitmap?.let { image ->
                            Image(
                                bitmap = image,
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                    },
                    failure = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_cancel_presentation_24),
                            contentDescription = "",
                            modifier = modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                )
                Text(
                    text = gridItemTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    minLines = 1
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeGrid() {
    HomeGridItem(
        gridItemTitle = "",
        gridItemImgUri = "",
        gridItemOnClickEvent = {}
    )
}