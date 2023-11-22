package com.example.whats_eat

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun WhatsEatNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LogIn.route
    ) {
    /*
        composable(
            route = LogIn.route,
            content = { LogInPage() }
        )
        composable(
            route = SignIn.route,
            content = { SignInPage() }
        )
        composable(
            route = FindPWD.route,
            content = { FindPWDPage() }
        )
        composable(
            route = Home.route,
            content = { HomePage() }
        )
        */
    }
}