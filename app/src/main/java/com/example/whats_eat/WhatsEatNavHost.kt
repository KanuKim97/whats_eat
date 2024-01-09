package com.example.whats_eat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.whats_eat.presenter.items.common.AppScaffold
import com.example.whats_eat.presenter.pages.CollectionList
import com.example.whats_eat.presenter.pages.DetailPlaceInfoPage
import com.example.whats_eat.presenter.pages.HomePage
import com.example.whats_eat.viewModel.DetailPlaceViewModel
import com.example.whats_eat.viewModel.HomeViewModel

@Composable
fun WhatsEatNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Home.route
    ) {
        composable(route = Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val mainBannerItems by homeViewModel.mainBannerItems.collectAsState(arrayListOf())
            val mainGridItems by homeViewModel.mainGridItems.collectAsState(arrayListOf())

            AppScaffold(
                navController = navController,
                content = {
                    HomePage(
                        mainBannerItems = mainBannerItems,
                        mainGridItems = mainGridItems,
                        getMainBannerItems = homeViewModel::getMainBannerItems,
                        getMainGridItems = homeViewModel::getSubGridViewItems,
                        navController = navController
                    )
                }
            )
        }
        composable(route = Collection.route) {

            AppScaffold(
                navController = navController,
                content = { CollectionList(listItems = arrayListOf()) }
            )
        }
        composable(
            route = DetailPlaceInfo.routeWithPlaceID,
            arguments = DetailPlaceInfo.argument
        ) { navBackStackEntry ->
            val placeID = navBackStackEntry.arguments?.getString(DetailPlaceInfo.placeID)
            val detailPlaceViewModel: DetailPlaceViewModel = hiltViewModel()
            val detailPlaceInfo by detailPlaceViewModel.detailPlaceResult.observeAsState()

            AppScaffold(
                navController = navController,
                content = {
                    DetailPlaceInfoPage(
                        placeID = placeID,
                        detailPlaceInfo = detailPlaceInfo,
                        getDetailPlaceInfo = detailPlaceViewModel::getDetailPlaceItem
                    )
                }
            )
        }
    }
}