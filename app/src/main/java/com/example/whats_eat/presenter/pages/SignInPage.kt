package com.example.whats_eat.presenter.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whats_eat.R
import com.example.whats_eat.presenter.items.common.TitleRow
import com.example.whats_eat.presenter.items.signin.SignInSection

@Composable
fun SignInPage(
    modifier: Modifier = Modifier,
    onLogInBtnClick: () -> Unit,
    onSignInBtnClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        var fullNameValue by remember { mutableStateOf("") }
        var userNameValue by remember { mutableStateOf("") }
        var emailValue by remember { mutableStateOf("") }
        var pwdValue by remember { mutableStateOf("") }
        var confPWDValue by remember { mutableStateOf("") }

        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
                content = {
                    TitleRow(
                        modifier = modifier,
                        titleText = stringResource(id = R.string.SignIn_Title),
                        subTitleText = stringResource(id = R.string.SignIn_SubTitle)
                    )
                }
            )
            Spacer(modifier = modifier.size(100.dp))
            SignInSection(
                modifier = modifier,
                fullNameValue = fullNameValue,
                userNameValue = userNameValue,
                emailValue = emailValue,
                pwdValue = pwdValue,
                confPWDValue = confPWDValue,
                onFullNameValueChanged = { fullName -> fullNameValue = fullName },
                onUserNameValueChanged = { userName -> userNameValue = userName },
                onEmailValueChanged = { email -> emailValue = email },
                onPWDValueChanged = { password -> pwdValue = password },
                onConfPWDValueChanged = { confPassword -> confPWDValue = confPassword },
                onSignInBtnClick = onSignInBtnClick,
                onLogInBtnClick = onLogInBtnClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInPage() {
    SignInPage(onLogInBtnClick = {}, onSignInBtnClick = {})
}