package com.example.home.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.HomeRoute
import com.example.home.HomeViewModel

const val homeRoute = "Home"

fun NavGraphBuilder.homeScreen(
    navigateToDetail: (String) -> Unit,
) {
    composable(
        route = homeRoute,
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
        val homeViewModel = hiltViewModel<HomeViewModel>()
        val bannerUiState by homeViewModel.bannerUiState.collectAsStateWithLifecycle()
        val gridUiState by homeViewModel.itemGridUiState.collectAsStateWithLifecycle()

        HomeRoute(
            navigateToDetail = navigateToDetail,
            getBannerUiState = homeViewModel::getBannerUiState,
            getItemGridUiState = homeViewModel::getItemGridUiState,
            getMainBannerState = bannerUiState,
            getItemsState = gridUiState
        )
    }
}