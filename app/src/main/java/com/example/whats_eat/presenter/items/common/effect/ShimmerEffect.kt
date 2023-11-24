package com.example.whats_eat.presenter.items.common.effect

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ShimmerEffect(
    modifier: Modifier,
    isLoading: Boolean,
    contentLoading: @Composable () -> Unit,
    contentAfterLoading: @Composable () -> Unit
) {
    if (isLoading) {
        Box(modifier = modifier.shimmerEffect(), content = { contentLoading() })
    } else {
        contentAfterLoading()
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    val shimmerColor = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val transitionAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                delayMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ),
        label = "shimmerAnimation"
    )

    background(
        brush = Brush.linearGradient(
            colors = shimmerColor,
            start = Offset.Zero,
            end = Offset(x = transitionAnim.value, y = transitionAnim.value)
        )
    )
}