package com.example.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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
                content = {
                    CircularProgressIndicator(
                        modifier = modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        strokeWidth = 1.dp
                    )
                }
            )
        },
        success = success,
        failure = failure
    )
}