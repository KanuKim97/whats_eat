package com.example.whats_eat.presenter.items.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.whats_eat.R

@Composable
fun TextInputField(
    modifier: Modifier,
    textValue: String,
    textLabel: String,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    leadingIcon: @Composable () -> Unit,
    onTextValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = onTextValueChanged,
        modifier = modifier,
        label = {
            Text(
                text = textLabel,
                fontSize = fontSize,
                fontWeight = fontWeight,
            )
        },
        leadingIcon = leadingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        shape = ShapeDefaults.Large
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTextInputField() {
    TextInputField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        textValue = "",
        textLabel = "이메일",
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = stringResource(id = R.string.LogIn_Email_Hint)
            )
        },
        onTextValueChanged = {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        keyboardActions = KeyboardActions.Default
    )
}
