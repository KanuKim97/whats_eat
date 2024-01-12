package com.example.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.detail.DetailScreen

const val detailRoute = "Detail"
const val placeID = "placeID"

fun NavController.onNavigateDetail(placeID: String) {
    this.navigate("$detailRoute/$placeID")
}

fun NavGraphBuilder.detailScreen() {
    composable(
        route = "$detailRoute/{$placeID}",
        arguments = listOf(navArgument(placeID) { type = NavType.StringType })
    ) {
        DetailScreen()
    }
}