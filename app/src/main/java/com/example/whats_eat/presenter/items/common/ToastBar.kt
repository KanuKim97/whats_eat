package com.example.whats_eat.presenter.items.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ToastBar(
    contentIcon: ImageVector,
    contentString: String,
    dismissAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier
            .wrapContentSize()
            .padding(8.dp),
        dismissAction = {
            IconButton(
                onClick = dismissAction,
                content = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            )
        },
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        imageVector = contentIcon,
                        contentDescription = "Warning"
                    )
                    Spacer(modifier = modifier.size(8.dp))
                    Text(text = contentString)
                }
            )
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewToastBar() {
    ToastBar(
        contentIcon = Icons.Default.Warning,
        contentString = "아이디와 비밀번호를 다시 확인해주세요.",
        dismissAction = {  }
    )
}