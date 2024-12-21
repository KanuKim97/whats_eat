package com.kanukim97.home.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kanukim97.home.HomeScreenRoot
import kotlinx.serialization.Serializable

fun NavGraphBuilder.homeScreen(navigateToDetail: (String) -> Unit) {
    composable<HomeRoute>(
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        }
    ) {
        HomeScreenRoot(navigateToDetail)
    }
}

@Serializable
data object HomeRoute {
    const val ROUTE = "Home"
}