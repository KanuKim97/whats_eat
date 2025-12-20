package com.kanukim97.whats_eat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kanukim97.designsystem.theme.EatTheme
import com.kanukim97.whats_eat.ui.WhatsEatApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EatTheme { WhatsEatApp() } }
    }
}
