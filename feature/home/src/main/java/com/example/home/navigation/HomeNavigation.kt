package com.example.home.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.home.HomeRoute
import com.example.home.HomeScreen

const val homeRoute = "Home"

fun NavController.onNavigateHome() {
    this.navigate(homeRoute)
}

fun NavGraphBuilder.homeScreen() {
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
        content = { HomeRoute() }
    )
}