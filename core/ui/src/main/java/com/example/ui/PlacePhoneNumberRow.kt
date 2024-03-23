package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.EatTypography
import com.example.designsystem.theme.Gray
import com.example.ui.preview.ComponentPreview

@Composable
fun PlacePhoneNumberRow(
    phoneNumber: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = 3.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Icon(
                imageVector = EatIcons.callBordered,
                contentDescription = "Icon",
                tint = Gray
            )
            Text(
                text = phoneNumber,
                style = EatTypography.labelLarge
            )
        }
    )
}

@ComponentPreview
@Composable
fun PreviewPlacePhoneNumber() {
    EatTheme {
        PlacePhoneNumberRow(phoneNumber = "02-999-9999")
    }
}