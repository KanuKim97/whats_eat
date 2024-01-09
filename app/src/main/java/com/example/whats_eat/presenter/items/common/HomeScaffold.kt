package com.example.whats_eat.presenter.items.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whats_eat.Collection
import com.example.whats_eat.Home
import com.example.whats_eat.Profile

@Composable
fun AppScaffold(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                onHomeBtnClick = { navController.navigate(Home.route) },
                onCollectionBtnClick = { navController.navigate(Collection.route) }
            )
        },
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAppScaffold() {
    AppScaffold(
        modifier = Modifier,
        navController = rememberNavController(),
        content = {}
    )
}