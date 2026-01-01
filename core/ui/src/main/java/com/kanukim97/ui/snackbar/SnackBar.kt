package com.kanukim97.ui.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WhatsEatToast(
    content: @Composable () -> Unit,
    action: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .background(
                color = Color(0xFF000000),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .heightIn(min = 48.dp)
            .padding(horizontal = 8.dp)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            content()

            action?.invoke()
        }
    }
}