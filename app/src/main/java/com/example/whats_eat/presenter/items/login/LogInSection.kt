package com.example.whats_eat.presenter.items.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whats_eat.R
import com.example.whats_eat.presenter.items.common.PWDInputField
import com.example.whats_eat.presenter.items.common.TextButton
import com.example.whats_eat.presenter.items.common.TextInputField

@Composable
fun LogInSection(
    modifier: Modifier,
    onFindPWDBtnClick: () -> Unit,
    onLogInBtnClick: () -> Unit,
    onSignInBtnClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TextInputField(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            textValue = "",
            textLabel = stringResource(id = R.string.LogIn_Email_Hint),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "") },
            onTextValueChanged = {  },
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default
        )
        PWDInputField(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            textValue = "",
            textLabel = stringResource(id = R.string.LogIn_Password_Hint),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            onTextValueChanged = {  },
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default
        )
        TextButton(
            onClick = onFindPWDBtnClick,
            modifier = modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(5.dp),
            btnTextContent = stringResource(id = R.string.FindPassword_Btn),
            btnColors = Color.White,
            btnElevation = 10.dp,
            textColor = Color.DarkGray,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        TextButton(
            onClick = onLogInBtnClick,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            btnTextContent = stringResource(id = R.string.LogIn_Btn),
            btnColors = Color.Black,
            btnElevation = 10.dp,
            textColor = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        TextButton(
            onClick = onSignInBtnClick,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            btnTextContent = stringResource(id = R.string.SignUp_Btn),
            btnColors = Color.Black,
            btnElevation = 10.dp,
            textColor = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogInSection() {
    LogInSection(
        modifier = Modifier,
        onFindPWDBtnClick = {/*TODO*/},
        onLogInBtnClick = {/*TODO*/},
        onSignInBtnClick = {/*TODO*/}
    )
}