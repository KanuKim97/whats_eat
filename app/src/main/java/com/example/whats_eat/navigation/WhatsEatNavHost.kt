package com.example.whats_eat.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.collection.navigation.collectionScreen
import com.example.detail.navigation.detailScreen
import com.example.detail.navigation.onNavigateDetail
import com.example.home.navigation.homeRoute
import com.example.home.navigation.homeScreen

@Composable
fun WhatsEatNavHost(
    scaffoldPaddingValues: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = homeRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        homeScreen(
            scaffoldPaddingValues = scaffoldPaddingValues,
            navigateToDetail = { id -> navController.onNavigateDetail(id) }
        )
        collectionScreen(scaffoldPaddingValues = scaffoldPaddingValues)
        detailScreen()
    }
}