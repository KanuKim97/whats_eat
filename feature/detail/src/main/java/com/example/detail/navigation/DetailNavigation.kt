package com.example.detail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.detail.DetailRoute
import com.example.detail.DetailViewModel

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
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        }
    ) {
        val detailViewModel = hiltViewModel<DetailViewModel>()
        val detailUiState by detailViewModel.detailUiState.collectAsStateWithLifecycle()
        val saveCollectionState by detailViewModel.saveCollectionState.collectAsStateWithLifecycle()

        val scrollState = rememberScrollState()

        DetailRoute(
            detailUiState = detailUiState,
            saveCollectionUiState = saveCollectionState,
            scrollState = scrollState,
            saveCollection = detailViewModel::saveCollection
        )
    }
}