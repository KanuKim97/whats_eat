package com.example.whats_eat

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface NavDestination { val route: String }

object LogIn: NavDestination { override val route: String = "LogInPage" }

object SignIn: NavDestination { override val route: String = "SignInPage" }

object FindPWD: NavDestination { override val route: String = "FindPWDPage" }

object Home: NavDestination { override val route: String = "HomePage" }

object DetailPlaceInfo: NavDestination {
    override val route: String = "DetailPlaceInfoPage"
    const val placeID = "Place_ID"
    val routeWithPlaceID: String = "$route/{$placeID}"
    val argument: List<NamedNavArgument> = listOf(navArgument(placeID) { type = NavType.StringType })
}

object Collection: NavDestination { override val route: String = "CollectionPage" }

object Profile: NavDestination { override val route: String = "ProfilePage" }