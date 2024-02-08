package com.example.whats_eat.navigation

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.collection.navigation.collectionScreen
import com.example.collection.navigation.toCollectionScreen
import com.example.detail.navigation.detailScreen
import com.example.detail.navigation.onNavigateDetail
import com.example.home.navigation.homeRoute
import com.example.home.navigation.homeScreen

@RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION])
@Composable
fun WhatsEatNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = homeRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        homeScreen(
            navigateToDetail = { id -> navController.onNavigateDetail(id) },
            navigateToCollection = { navController.toCollectionScreen() }
        )
        collectionScreen(navigationIconClick = { navController.popBackStack() })
        detailScreen(
            navigationIconOnClick = { navController.popBackStack() },
            navigateToCollectionScreen = { navController.toCollectionScreen() }
        )
    }
}