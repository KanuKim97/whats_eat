package com.example.detail.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatHorizontalPager
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.theme.Gray550
import com.example.designsystem.theme.LogoColor

@Composable
fun PlaceImagePager(
    placeImgList: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { placeImgList.size }

    AnimatedVisibility(
        visible = placeImgList.isNotEmpty(),
        enter = fadeIn(),
    ) {
        EatHorizontalPager(
            pagerState = pagerState,
            modifier = modifier
        ) { index ->
            EatImageLoader(
                imageModel = placeImgList[index],
                modifier = modifier.fillMaxSize()
            )
        }
    }
}


@Composable
internal fun PlaceImgPagerWithIndicator(
    placeImgList: List<String>
) {
    val pagerState = rememberPagerState { placeImgList.size }

    AnimatedVisibility(
        visible = placeImgList.isNotEmpty(),
        enter = fadeIn()
    ) {
        Box(modifier = Modifier) {
            EatHorizontalPager(
                pagerState,
                Modifier
            ) {
                EatImageLoader(
                    imageModel = placeImgList[it],
                    modifier = Modifier
                )
            }
        }
    }
}


@Composable
internal fun PlaceImgPageIndicatorView(
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val indicatorColor by animateColorAsState(
        targetValue = if (isSelected) {
            LogoColor
        } else {
            Gray550
        },
        animationSpec = tween(),
        label = "Pager Indicator Color"
    )

    val indicatorWidth: Dp by animateDpAsState(
        targetValue = if (isSelected) {
            30.dp
        } else {
            10.dp
        },
        animationSpec = tween(),
        label = "Pager Indicator Width"
    )


    Canvas(
        modifier = modifier.size(
            width = indicatorWidth,
            height = 10.dp
        )
    ) {
        drawRoundRect(
            color = indicatorColor,
            topLeft = Offset.Zero,
            size = Size(
                width = this.size.width,
                height = this.size.height
            ),
            cornerRadius = CornerRadius(x = 10.dp.toPx(), y= 10.dp.toPx())
        )
    }
}