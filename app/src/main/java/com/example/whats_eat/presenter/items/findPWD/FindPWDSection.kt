package com.example.whats_eat.presenter.items.findPWD

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
fun FindPWDSection(
    modifier: Modifier,
    emailValue: String,
    onEmailValueChanged: (String) -> Unit,
    onFindPWDBtnClick: () -> Unit,
    onLogInPageClick: () -> Unit,
    onSignInPageClick: () -> Unit
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
            textValue = emailValue,
            textLabel = stringResource(id = R.string.LogIn_Email_Hint),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "") },
            onTextValueChanged = onEmailValueChanged,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions(onDone = KeyboardActions.Default.onDone)
        )
        Spacer(modifier = modifier.size(70.dp))
        TextButton(
            onClick = onFindPWDBtnClick,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            btnTextContent = stringResource(id = R.string.FindPassword_Btn),
            btnColors = Color.Black,
            btnElevation = 5.dp,
            textColor = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        TextButton(
            onClick = onLogInPageClick,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            btnTextContent = stringResource(id = R.string.ToLogIn_Btn),
            btnColors = Color.Black,
            btnElevation = 5.dp,
            textColor = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        TextButton(
            onClick = onSignInPageClick,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            btnTextContent = stringResource(id = R.string.ToSignUp_Btn),
            btnColors = Color.Black,
            btnElevation = 5.dp,
            textColor = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFindPWDSection() {
    FindPWDSection(
        modifier = Modifier,
        emailValue = "",
        onEmailValueChanged = {},
        onFindPWDBtnClick = {},
        onLogInPageClick = {},
        onSignInPageClick = {}
    )
}