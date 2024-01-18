package com.example.home.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.HomeRoute

const val homeRoute = "Home"

fun NavController.onNavigateHome() {
    this.navigate(homeRoute)
}

fun NavGraphBuilder.homeScreen(
    scaffoldPaddingValues: PaddingValues,
    navigateToDetail: (String) -> Unit
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
        },
        content = {
            HomeRoute(
                scaffoldPaddingValues = scaffoldPaddingValues,
                navigateToDetail = navigateToDetail
            )
        }
    )
}