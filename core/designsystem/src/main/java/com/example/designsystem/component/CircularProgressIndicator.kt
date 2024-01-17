package com.example.designsystem.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun EatCircularProgressIndicator() {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.primaryContainer,
        strokeWidth = 3.dp
    )
}