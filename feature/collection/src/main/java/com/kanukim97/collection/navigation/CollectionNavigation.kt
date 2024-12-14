package com.example.collection.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.collection.CollectionRoute
import com.example.collection.CollectionViewModel

const val collectionRoute = "Collection"

fun NavController.toCollectionScreen() {
    this.navigate(collectionRoute)
}

fun NavGraphBuilder.collectionScreen() {
    composable(
        route = collectionRoute,
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
            val collectionViewModel = hiltViewModel<CollectionViewModel>()
            val readAllContentUiState by collectionViewModel.readAllCollectionUiState.collectAsStateWithLifecycle()

            CollectionRoute(readAllContentUiState)
        }
    )
}