package com.example.ui

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.designsystem.component.EatAlertDialog
import com.example.designsystem.component.EatOutlinedTextButton
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.EatTypography
import com.example.ui.preview.ComponentPreview

@Composable
fun PermissionAlertDialog(
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
) {
    EatAlertDialog(
        modifier = modifier,
        icon = icon,
        title = {
            Text(
                text = "권한을 허용해주세요!",
                fontWeight = FontWeight.Bold,
                style = EatTypography.titleLarge
            )
        },
        text = {
            Text(
                text = "이 앱을 사용하기 위해 \n" +
                    "위치 사용 권한을 허용해야합니다.\n" +
                    "허용하시겠습니까?",
                fontWeight = FontWeight.Medium,
                style = EatTypography.bodySmall
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            EatOutlinedTextButton(
                onClick = onOkClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                content = { Text(text = "네", style = EatTypography.labelLarge) }
            )
        },
        dismissButton = {
            EatOutlinedTextButton(
                onClick = onDismissClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.inverseSurface
                ),
                content = { Text(text = "아니요", style = EatTypography.labelLarge) }
            )
        }
    )
}

@ComponentPreview
@Composable
fun Preview() {
    EatTheme {
        PermissionAlertDialog(
            onDismiss = { /*TODO*/ },
            onOkClick = {},
            onDismissClick = {},
            icon = { Icon(imageVector = EatIcons.locationOnBordered, contentDescription = "") }
        )
    }
}