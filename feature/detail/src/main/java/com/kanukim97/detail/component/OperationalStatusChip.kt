package com.kanukim97.detail.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OperationalStatusChip(
    isOpened: () -> Boolean,
    modifier: Modifier = Modifier
) {
    val status = isOpened.invoke()
    val chipBackgroundColor by animateColorAsState(
        if (status) Color(0xC3C8FCC6) else Color(0xC3FCC6C6)
    )
    val chipContentColor by animateColorAsState(
        if (status) Color(0xFF217523) else Color(0xFF753521)
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(chipBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (status) "Open Now" else "Closed",
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            color = chipContentColor
        )
    }
}