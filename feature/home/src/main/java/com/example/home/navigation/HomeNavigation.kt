package com.example.home.navigation

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.HomeRoute

const val homeRoute = "Home"

@RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION])
fun NavGraphBuilder.homeScreen(
    navigateToDetail: (String) -> Unit,
    navigateToCollection: () -> Unit
) {
    composable(
        route = homeRoute,
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
            )
        }
    ) {
        HomeRoute(
            navigateToDetail = navigateToDetail,
            navigateToCollection = navigateToCollection
        )
    }
}