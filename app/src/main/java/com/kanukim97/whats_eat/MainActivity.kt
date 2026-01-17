package com.kanukim97.whats_eat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.kanukim97.designsystem.theme.EatTheme
import com.kanukim97.whats_eat.navigation.WhatsEatNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            EatTheme {
                val navController = rememberNavController()

                WhatsEatNavHost(navController)
            }
        }
    }
}
