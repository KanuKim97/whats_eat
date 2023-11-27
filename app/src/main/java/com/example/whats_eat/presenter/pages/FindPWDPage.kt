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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whats_eat.LogIn
import com.example.whats_eat.R
import com.example.whats_eat.SignIn
import com.example.whats_eat.presenter.items.common.TitleRow
import com.example.whats_eat.presenter.items.findPWD.FindPWDSection

@Composable
fun FindPWDPage(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var emailValue by remember { mutableStateOf("") }

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
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                content = {
                    TitleRow(
                        modifier = modifier,
                        titleText = stringResource(id = R.string.FindPassword_Title),
                        subTitleText = stringResource(id = R.string.FindPassword_SubTitle)
                    )
                }
            )
            Spacer(modifier = modifier.size(100.dp))
            FindPWDSection(
                modifier = modifier,
                emailValue = emailValue,
                onEmailValueChanged = { email -> emailValue = email },
                onLogInPageClick = { navController.navigate(LogIn.route) },
                onSignInPageClick = { navController.navigate(SignIn.route) },
                onFindPWDBtnClick = {  }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFindPWDPage() {
    FindPWDPage(navController = rememberNavController())
}