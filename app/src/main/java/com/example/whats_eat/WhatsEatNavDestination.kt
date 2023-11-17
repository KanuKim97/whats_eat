package com.example.whats_eat

interface NavDestination { val route: String }

object LogIn: NavDestination { override val route: String = "LogInPage" }

object SignIn: NavDestination { override val route: String = "SignInPage" }

object FindPWD: NavDestination { override val route: String = "FindPWDPage" }

object Home: NavDestination { override val route: String = "HomePage" }

object DetailPlaceInfo: NavDestination { override val route: String = "DetailPlaceInfoPage" }

object Collection: NavDestination { override val route: String = "CollectionPage" }

object Profile: NavDestination { override val route: String = "ProfilePage" }