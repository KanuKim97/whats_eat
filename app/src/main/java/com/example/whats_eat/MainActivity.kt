package com.example.whats_eat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.designsystem.theme.EatTheme
import com.example.whats_eat.ui.WhatsEatApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EatTheme { WhatsEatApp() } }
    }
}
