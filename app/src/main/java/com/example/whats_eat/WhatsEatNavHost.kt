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
import com.example.whats_eat.presenter.pages.FindPWDPage
import com.example.whats_eat.presenter.pages.HomePage
import com.example.whats_eat.presenter.pages.LogInPage
import com.example.whats_eat.presenter.pages.ProfilePage
import com.example.whats_eat.presenter.pages.SignInPage
import com.example.whats_eat.util.AuthState
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
            val logInState by logInViewModel.userLogInResult.collectAsState(AuthState.IsLoading(false))

            LogInPage(
                userLogIn = logInViewModel::userLogIn,
                userLogInState = logInState,
                navController = navController
            )
        }
        composable(route = SignIn.route) {
            val signInViewModel: SignInViewModel = hiltViewModel()
            val signInState by signInViewModel.signInState.collectAsState(AuthState.IsLoading(false))

            SignInPage(
                signIn = signInViewModel::createAccount,
                signInState = signInState,
                navController = navController
            )
        }
        composable(route = FindPWD.route) {
            val findPWDViewModel: FindPWDViewModel = hiltViewModel()
            val findPWDState by findPWDViewModel.sendEmailState.collectAsState(AuthState.IsLoading(false))

            FindPWDPage(
                findPWD = findPWDViewModel::sendPWDResetEmail,
                findPWDState = findPWDState,
                navController = navController
            )
        }
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