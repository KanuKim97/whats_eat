package com.example.ui

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.designsystem.component.EatAlertDialog
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.EatTheme
import com.example.ui.preview.ComponentPreview

@Composable
fun PermissionAlertDialog(
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
) {
    EatAlertDialog(
        modifier = modifier,
        icon = icon,
        titleText = "권한을 허용해주세요!",
        contentText = "이 앱을 사용하기 위해 \n" +
            "위치 사용 권한을 허용해야합니다.\n" +
            "허용하시겠습니까?",
        onDismissRequest = onDismiss,
        onDismissClick = onDismissClick,
        onConfirmClick = onConfirmClick
    )
}

@ComponentPreview
@Composable
fun Preview() {
    EatTheme {
        PermissionAlertDialog(
            onDismiss = { /*TODO*/ },
            onConfirmClick = {},
            onDismissClick = {},
            icon = { Icon(imageVector = EatIcons.locationOnBordered, contentDescription = "") }
        )
    }
}