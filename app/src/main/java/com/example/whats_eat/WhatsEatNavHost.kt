package com.example.whats_eat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.whats_eat.presenter.items.common.AppScaffold
import com.example.whats_eat.presenter.pages.CollectionList
import com.example.whats_eat.presenter.pages.DetailPlaceInfoPage
import com.example.whats_eat.presenter.pages.FindPWDPage
import com.example.whats_eat.presenter.pages.HomePage
import com.example.whats_eat.presenter.pages.LogInPage
import com.example.whats_eat.presenter.pages.ProfilePage
import com.example.whats_eat.presenter.pages.SignInPage
import com.example.whats_eat.viewModel.ApplicationViewModel
import com.example.whats_eat.viewModel.CollectionViewModel
import com.example.whats_eat.viewModel.DetailPlaceViewModel
import com.example.whats_eat.viewModel.FindPWDViewModel
import com.example.whats_eat.viewModel.HomeViewModel
import com.example.whats_eat.viewModel.LoginViewModel
import com.example.whats_eat.viewModel.ProfileViewModel
import com.example.whats_eat.viewModel.SignInViewModel

@Composable
fun WhatsEatNavHost(navController: NavHostController) {
    val appViewModel: ApplicationViewModel = hiltViewModel()
    val userState by appViewModel.userState.collectAsState()
    val startDestination = if (userState) { Home.route } else { LogIn.route }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = LogIn.route) {
            val logInViewModel: LoginViewModel = hiltViewModel()

            LogInPage(navController = navController)
        }
        composable(route = SignIn.route) {
            val signInViewModel: SignInViewModel = hiltViewModel()

            SignInPage(navController = navController)
        }
        composable(route = FindPWD.route) {
            val findPWDViewModel: FindPWDViewModel = hiltViewModel()

            FindPWDPage(navController = navController)
        }
        composable(route = Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()

            AppScaffold(
                navController = navController,
                content = { HomePage() }
            )
        }
        composable(route = Profile.route) {
            val profileViewModel: ProfileViewModel = hiltViewModel()

            AppScaffold(
                navController = navController,
                content = { ProfilePage() }
            )
        }
        composable(route = Collection.route) {
            val collectionViewModel: CollectionViewModel = hiltViewModel()

            AppScaffold(
                navController = navController,
                content = { CollectionList(listItems = arrayListOf()) }
            )
        }
        composable(route = DetailPlaceInfo.route) {
            val detailPlaceViewModel: DetailPlaceViewModel = hiltViewModel()

            AppScaffold(
                navController = navController,
                content = { DetailPlaceInfoPage() }
            )
        }
    }
}