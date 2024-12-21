package com.kanukim97.collection.navigation

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
import com.kanukim97.collection.CollectionScreenRoot
import com.kanukim97.collection.CollectionViewModel
import kotlinx.serialization.Serializable

fun NavController.toCollectionScreen() {
    this.navigate(CollectionRoute)
}

fun NavGraphBuilder.collectionScreen() {
    composable<CollectionRoute>(
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

            CollectionScreenRoot(readAllContentUiState)
        }
    )
}

@Serializable
data object CollectionRoute {
    const val ROUTE = "Collection"
}