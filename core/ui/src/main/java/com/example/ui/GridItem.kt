package com.example.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.theme.EatShape
import com.example.designsystem.theme.Typography
import com.example.ui.preview.ComponentPreview

@Composable
fun GridItem(
    itemTitle: String,
    itemImgUri: String,
    itemOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .clickable(onClick = itemOnClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            content = {
                EatImageLoader(
                    imageModel = itemImgUri,
                    modifier = modifier
                        .size(160.dp)
                        .clip(shape = EatShape.medium),
                    success = {  imageState, _ ->
                        imageState.imageBitmap?.let { bitmap ->
                            Image(
                                bitmap = bitmap,
                                contentDescription = "image"
                            )
                        }
                    },
                    failure = {
                        Text(
                            text = "이미지를 불러오지 못했습니다.",
                            style = Typography.labelLarge
                        )
                    }
                )
                Text(
                    text = itemTitle,
                    modifier = modifier
                        .width(160.dp)
                        .wrapContentHeight(),
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = Typography.labelLarge
                )
            }
        )
    }
}

@ComponentPreview
@Composable
fun PreviewItems() {
    GridItem(
        itemTitle = "000상회",
        itemImgUri = "",
        itemOnClick = { /*TODO*/ }
    )
}