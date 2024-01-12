package com.example.collection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.collection.CollectionScreen

const val collectionRoute = "Collection"

fun NavController.onNavigateCollection() {
    this.navigate(collectionRoute)
}

fun NavGraphBuilder.collectionScreen() {
    composable(route = collectionRoute) {
        CollectionScreen()
    }
}