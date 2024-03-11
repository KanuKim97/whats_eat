package com.example.whats_eat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.designsystem.theme.EatTheme
import com.example.whats_eat.navigation.WhatsEatNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController: NavHostController = rememberNavController()
            EatTheme { WhatsEatNavHost(navController = navHostController) }
        }
    }
}
