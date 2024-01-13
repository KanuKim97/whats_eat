package com.example.whats_eat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.designsystem.component.BottomAppNavBar
import com.example.designsystem.component.BottomNavAppBarItem
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.Typography
import com.example.whats_eat.navigation.NavigationMenu
import com.example.whats_eat.navigation.WhatsEatNavHost
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController: NavHostController = rememberNavController()
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val navMenu = listOf(
                NavigationMenu.Home,
                NavigationMenu.Collection
            )

            EatTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomAppNavBar {
                            navMenu.forEach {  navigationMenu ->
                                BottomNavAppBarItem(
                                    onClick = { navHostController.navigate(navigationMenu.route) },
                                    selected = currentDestination
                                        ?.hierarchy
                                        ?.any { destination ->
                                            destination.route == navigationMenu.route
                                        } == true,
                                    icon = {
                                        Icon(
                                            imageVector = navigationMenu.icon,
                                            contentDescription = navigationMenu.screenName
                                        )
                                    },
                                    selectedIcon = {
                                        Icon(
                                            imageVector = navigationMenu.selectedIcon,
                                            contentDescription = navigationMenu.screenName
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = navigationMenu.screenName,
                                            fontWeight = FontWeight.Bold,
                                            style = Typography.labelMedium
                                        )
                                    }
                                )
                            }
                        }
                    },
                    content = { _ -> WhatsEatNavHost(navController = navHostController) }
                )
            }
        }
    }
}