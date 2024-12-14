package com.example.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.designsystem.theme.EatShape
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.EatTypography
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.glide.GlideImageState

@Composable
fun EatImageLoader(
    imageModel: Any?,
    modifier: Modifier = Modifier,
    success: @Composable (BoxScope.(GlideImageState.Success, Painter) -> Unit)? = null,
    failure: @Composable (BoxScope.(GlideImageState.Failure) -> Unit)? = null
) {
    GlideImage(
        imageModel = { imageModel },
        modifier = modifier,
        requestOptions = { RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL) },
        imageOptions = ImageOptions(
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        ),
        loading = {
            Box(
                modifier = modifier.matchParentSize(),
                contentAlignment = Alignment.Center,
                content = { EatCircularProgressIndicator() }
            )
        },
        success = success,
        failure = failure
    )
}

@Composable
fun EatImageLoader(
    imageModel: Any?,
    modifier: Modifier = Modifier
) {
    EatImageLoader(
        imageModel = imageModel,
        modifier = modifier.clip(EatShape.large),
        success = { imgState, _ ->
            imgState.imageBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    modifier = modifier.matchParentSize(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Image"
                )
            }
        },
        failure = {
            Box(
                modifier = modifier
                    .matchParentSize()
                    .background(Gray)
                    .clip(EatShape.large),
                contentAlignment = Alignment.Center,
                content = {
                    Text(
                        text = "이미지 불러오기에 실패하였습니다.",
                        style = EatTypography.labelLarge
                    )
                }
            )
        }
    )
}