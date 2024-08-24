package com.example.whats_eat.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.collection.navigation.collectionRoute
import com.example.collection.navigation.toCollectionScreen
import com.example.designsystem.component.EatCenterAlignedAppBar
import com.example.designsystem.component.EatLargeTopAppBar
import com.example.designsystem.icons.EatIcons
import com.example.detail.navigation.detailRoute
import com.example.home.navigation.homeRoute
import com.example.whats_eat.navigation.WhatsEatNavHost

@Composable
fun WhatsEatApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            currentRoute?.let { destination ->
                when {
                    destination.contains(homeRoute) -> {
                        EatLargeTopAppBar(
                            mainTitle = "내 주변 음식점",
                            subTitle = "오늘은 여기 어떤가요?",
                            actionIcon = EatIcons.CollectionOutlined,
                            actionIconOnClick = { navController.toCollectionScreen() }
                        )
                    }
                    destination.contains(detailRoute) -> {
                        EatCenterAlignedAppBar(
                            navigationIcon = EatIcons.arrowBackOutlined,
                            navigationIconOnClick = { navController.popBackStack() },
                            actions = {
                                IconButton(
                                    onClick = { navController.toCollectionScreen() },
                                    content = {
                                        Icon(
                                            imageVector = EatIcons.CollectionOutlined,
                                            contentDescription = "Collection"
                                        )
                                    }
                                )
                            }
                        )
                    }
                    destination.contains(collectionRoute) -> {
                        EatCenterAlignedAppBar(
                            navigationIcon = EatIcons.arrowBackOutlined,
                            navigationIconOnClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            content =  { WhatsEatNavHost(navController = navController) }
        )
    }
}