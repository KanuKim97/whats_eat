package com.kanukim97.whats_eat.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kanukim97.collection.navigation.collectionScreen
import com.kanukim97.detail.navigation.detailScreen
import com.kanukim97.detail.navigation.onNavigateDetail
import com.kanukim97.home.navigation.HomeRoute
import com.kanukim97.home.navigation.homeScreen

@Composable
fun WhatsEatNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        homeScreen(navigateToDetail = { id -> navController.onNavigateDetail(id) })
        collectionScreen()
        detailScreen()
    }
}