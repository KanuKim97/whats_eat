package com.example.designsystem.font

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.designsystem.R

val pretendard = FontFamily(
    Font(
        resId = R.font.pretendard_thin,
        weight = FontWeight.Thin,
    ),
    Font(
        resId = R.font.pretendard_extra_light,
        weight = FontWeight.ExtraLight
    ),
    Font(
        resId = R.font.pretendard_light,
        weight = FontWeight.Light,
    ),
    Font(
        resId = R.font.pretendard_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.pretendard_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.pretendard_semi_bold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.pretendard_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.pretendard_extra_bold,
        weight = FontWeight.ExtraBold
    ),
    Font(
        resId = R.font.pretendard_black,
        weight = FontWeight.Black
    )
)