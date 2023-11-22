package com.example.whats_eat.presenter.items.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.People
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
import com.example.whats_eat.presenter.items.common.TextButton
import com.example.whats_eat.presenter.items.common.TextInputField

@Composable
fun SignInSection(
    modifier: Modifier,
    fullNameValue: String,
    userNameValue: String,
    emailValue: String,
    pwdValue: String,
    confPWDValue: String,
    onFullNameValueChanged: (String) -> Unit,
    onUserNameValueChanged: (String) -> Unit,
    onEmailValueChanged: (String) -> Unit,
    onPWDValueChanged: (String) -> Unit,
    onConfPWDValueChanged: (String) -> Unit,
    onSignInBtnClick: () -> Unit,
    onLogInBtnClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TextInputField(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            textValue = fullNameValue,
            textLabel = stringResource(id = R.string.SignIn_FullName_Hint),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            leadingIcon = { Icon(imageVector = Icons.Default.People, contentDescription = "") },
            onTextValueChanged = onFullNameValueChanged,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions(onDone = KeyboardActions.Default.onNext)
        )
        TextInputField(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            textValue = userNameValue,
            textLabel = stringResource(id = R.string.SignIn_UserName_Hint),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            leadingIcon = { Icon(imageVector = Icons.Default.People, contentDescription = "") },
            onTextValueChanged = onUserNameValueChanged,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions(onDone = KeyboardActions.Default.onNext)
        )
        TextInputField(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            textValue = emailValue,
            textLabel = stringResource(id = R.string.SignIn_Email_Hint),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "") },
            onTextValueChanged = onEmailValueChanged,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions(onDone = KeyboardActions.Default.onNext)
        )
        TextInputField(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            textValue = pwdValue,
            textLabel = stringResource(id = R.string.SignIn_Password_Hint),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            leadingIcon = { Icon(imageVector = Icons.Default.Password, contentDescription = "") },
            onTextValueChanged = onPWDValueChanged,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions(onDone = KeyboardActions.Default.onNext)
        )
        TextInputField(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            textValue = confPWDValue,
            textLabel = stringResource(id = R.string.SignIn_ConfirmPassword_Hint),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            leadingIcon = { Icon(imageVector = Icons.Default.Password, contentDescription = "") },
            onTextValueChanged = onConfPWDValueChanged,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions(onDone = KeyboardActions.Default.onDone)
        )
        Spacer(modifier = modifier.size(30.dp))
        TextButton(
            onClick = onSignInBtnClick,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            btnTextContent = stringResource(id = R.string.SignUp_Btn),
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
                .wrapContentHeight(),
            btnTextContent = stringResource(id = R.string.ToLogIn_Btn),
            btnColors = Color.White,
            btnElevation = 10.dp,
            textColor = Color.DarkGray,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInPage() {
    SignInSection(
        modifier = Modifier,
        fullNameValue = "",
        userNameValue = "",
        emailValue = "",
        pwdValue = "",
        confPWDValue = "",
        onFullNameValueChanged = {},
        onUserNameValueChanged = {},
        onEmailValueChanged = {},
        onPWDValueChanged = {},
        onConfPWDValueChanged = {},
        onSignInBtnClick = {},
        onLogInBtnClick = {}
    )
}