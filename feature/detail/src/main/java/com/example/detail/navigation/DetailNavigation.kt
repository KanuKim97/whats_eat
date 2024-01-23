package com.example.detail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.detail.DetailRoute

const val detailRoute = "Detail"
const val placeIDArgs = "placeID"

internal class PlaceIdArgs(val placeID: String) {
    constructor(savedStateHandle: SavedStateHandle):
        this(placeID = checkNotNull(savedStateHandle[placeIDArgs]))
}

fun NavController.onNavigateDetail(placeID: String) {
    this.navigate("$detailRoute/$placeID")
}

fun NavGraphBuilder.detailScreen() {
    composable(
        route = "$detailRoute/{$placeIDArgs}",
        arguments = listOf(
            navArgument(placeIDArgs) { type = NavType.StringType }
        ),
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
        content = { DetailRoute() }
    )
}