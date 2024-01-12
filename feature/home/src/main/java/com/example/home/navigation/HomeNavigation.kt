package com.example.home.navigation

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
        content = { HomeRoute() }
    )
}