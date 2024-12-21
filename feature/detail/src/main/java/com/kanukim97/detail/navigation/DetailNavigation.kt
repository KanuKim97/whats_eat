package com.kanukim97.detail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kanukim97.detail.DetailScreenRoot
import kotlinx.serialization.Serializable

fun NavController.onNavigateDetail(placeID: String) {
    this.navigate(DetailRoute(placeID))
}

fun NavGraphBuilder.detailScreen() {
    composable<DetailRoute>(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            ) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        },
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
        val detailRoute: DetailRoute = it.toRoute()

        DetailScreenRoot(detailRoute.placeId)
    }
}

@Serializable
data class DetailRoute(val placeId: String) {
    companion object { const val ROUTE = "Detail" }
}