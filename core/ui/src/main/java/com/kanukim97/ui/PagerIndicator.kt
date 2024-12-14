package com.kanukim97.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Gray550
import com.example.designsystem.theme.LogoColor
import com.example.ui.preview.ComponentPreview

@Composable
fun EatPagerIndicators(
    numberOfPages: Int,
    selectedPage: Int = 0,
    space: Dp = 10.dp,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until numberOfPages) {
            val isSelected = i == selectedPage

            EatPagerIndicatorView(isSelected)
        }
    }
}

@Composable
fun EatPagerIndicatorView(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    defaultIndicatorColor: Color = Gray550,
    selectedIndicatorColor: Color = LogoColor,
    defaultIndicatorWidth: Dp = 10.dp,
    selectedIndicatorWidth: Dp = 20.dp
) {
    val indicatorColor by animateColorAsState(
        targetValue = if (isSelected) selectedIndicatorColor else defaultIndicatorColor,
        animationSpec = tween(),
        label = "Indicator Color"
    )

    val indicatorWidth by animateDpAsState(
        targetValue = if (isSelected) selectedIndicatorWidth else defaultIndicatorWidth,
        animationSpec = tween(),
        label = "Indicator Width"
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
                width = size.width,
                height = size.height
            ),
            cornerRadius = CornerRadius(
                defaultIndicatorWidth.toPx(),
                defaultIndicatorWidth.toPx()
            )
        )
    }
}