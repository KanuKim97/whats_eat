package com.example.designsystem.font

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.designsystem.R


val NotoSansKR = FontFamily(
    Font(
        resId = R.font.notosans_kr,
        style = FontStyle.Normal,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.notosans_kr,
        style = FontStyle.Normal,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.notosans_kr,
        style = FontStyle.Normal,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.notosans_kr,
        style = FontStyle.Normal,
        weight = FontWeight.ExtraBold
    ),
    Font(
        resId = R.font.notosans_kr,
        style = FontStyle.Normal,
        weight = FontWeight.Black
    )
)