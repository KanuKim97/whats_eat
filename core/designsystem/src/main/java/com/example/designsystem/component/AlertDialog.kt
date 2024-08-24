package com.example.designsystem.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.designsystem.theme.EatShape
import com.example.designsystem.theme.EatTypography

@Composable
fun EatAlertDialog(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        icon = icon,
        title = title,
        text = text,
        shape = EatShape.large,
        modifier = modifier
    )
}

@Composable
fun EatAlertDialog(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    titleText: String,
    contentText: String,
    onDismissRequest: () -> Unit,
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    EatAlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            EatOutlinedTextButton(
                onClick = onConfirmClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.inverseSurface
                ),
                content = { Text(text = "네", style = EatTypography.labelLarge) }
            )
        },
        dismissButton = {
            EatOutlinedTextButton(
                onClick = onDismissClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.inverseSurface
                ),
                content = { Text(text = "아니요", style = EatTypography.labelLarge) }
            )
        },
        icon = icon,
        title = {
            Text(
                text = titleText,
                fontWeight = FontWeight.Bold,
                style = EatTypography.titleLarge
            )
        },
        text = {
            Text(
                text = contentText,
                fontWeight = FontWeight.Medium,
                style = EatTypography.bodySmall
            )
        },
        modifier = modifier
    )
}