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
import com.example.whats_eat.presenter.items.login.LogInSection
import com.example.whats_eat.presenter.items.common.TitleRow

@Composable
fun LogInPage(
    modifier: Modifier = Modifier,
    onSignInBtnClick: () -> Unit,
    onFindPWDBtnClick: () -> Unit
) {
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
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
                        titleText = stringResource(id = R.string.LogIn_Title),
                        subTitleText = stringResource(id = R.string.LogIn_SubTitle)
                    )
                }
            )
            Spacer(modifier = modifier.size(100.dp))
            LogInSection(
                modifier = modifier,
                emailValue = emailValue,
                pwdValue = passwordValue,
                onEmailValueChanged = { email -> emailValue = email },
                onPWDValueChanged = { password -> passwordValue = password },
                onLogInBtnClick = {  },
                onSignInBtnClick = onSignInBtnClick,
                onFindPWDBtnClick = onFindPWDBtnClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogInPage() {
    LogInPage(
        onFindPWDBtnClick = { /*TODO*/ },
        onSignInBtnClick = { /*TODO*/ }
    )
}