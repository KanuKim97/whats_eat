package com.example.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.EatShape
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.EatTypography

@Composable
fun NoticeSnackBar(
    modifier: Modifier = Modifier,
    dismissAction: () -> Unit,
    shape: Shape = EatShape.medium,
    text: String,
) {
    Snackbar(
        modifier = modifier,
        dismissAction = {
            IconButton(
                onClick = dismissAction,
                content = {
                    Icon(
                        imageVector = EatIcons.CloseOutlined,
                        contentDescription = "Warning"
                    )
                }
            )
        },
        shape = shape,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        imageVector = EatIcons.WarningOutlined,
                        contentDescription = "Warning"
                    )
                    Spacer(modifier = modifier.size(8.dp))
                    Text(
                        text = text,
                        style = EatTypography.labelLarge
                    )
                }
            )
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSnackBar() {
    EatTheme {
        NoticeSnackBar(dismissAction = { /*TODO*/ }, text = "데이터를 켜주세요!")
    }
}