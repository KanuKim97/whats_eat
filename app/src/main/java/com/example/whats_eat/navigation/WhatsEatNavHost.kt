package com.example.whats_eat.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.collection.navigation.collectionScreen
import com.example.detail.navigation.detailScreen
import com.example.home.navigation.homeScreen

@Composable
fun WhatsEatNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "Home",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        homeScreen()
        collectionScreen()
        detailScreen()
    }
}