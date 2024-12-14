package com.example.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightColorTheme = lightColorScheme(
    primary = LogoColor,
    onPrimary = White,
    primaryContainer = BrightBlue,
    onPrimaryContainer = BrandBlue,
    secondary = Brand,
    onSecondary = White,
    secondaryContainer = BrightBlue,
    onSecondaryContainer = BrandBlue,
    background = White,
    surface = White,
    inverseSurface = Black
)

private val darkColorTheme = darkColorScheme(
    primary = LogoColor,
    onPrimary = Black,
    primaryContainer = BrightBlue,
    onPrimaryContainer = BrandBlue,
    secondary = Brand,
    onSecondary = Black,
    secondaryContainer = BrightBlue,
    onSecondaryContainer = BrandBlue,
    background = Black,
    surface = Black,
    inverseSurface = White
)

@Composable
fun EatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val colorScheme = if (darkTheme) { darkColorTheme } else { lightColorTheme }

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = EatShape,
        typography = EatTypography,
        content = content
    )
}