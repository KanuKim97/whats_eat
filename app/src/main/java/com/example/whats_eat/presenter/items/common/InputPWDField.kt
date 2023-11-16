package com.example.whats_eat.presenter.items.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.whats_eat.R

@Composable
fun PWDInputField(
    modifier: Modifier,
    textValue: String,
    textLabel: String,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    onTextValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

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
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "") },
        trailingIcon = {
            val toggleIcon: ImageVector = if (passwordVisible) {
                Icons.Default.Visibility
            } else {
                Icons.Default.VisibilityOff
            }
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
                content = {
                    Icon(
                        imageVector = toggleIcon,
                        contentDescription = stringResource(id = R.string.LogIn_Password_Hint)
                    )
                }
            )
        },
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        shape = ShapeDefaults.Large
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPWDInputField() {
    PWDInputField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        textValue = "",
        textLabel = "비밀번호",
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        onTextValueChanged = {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions.Default
    )
}
