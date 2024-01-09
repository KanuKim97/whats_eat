package com.example.whats_eat.presenter.items.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whats_eat.R

@Composable
fun TitleRow(
    modifier: Modifier,
    titleText: String,
    subTitleText: String
) {
    Column(
        modifier = modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.logoImg_Common),
            modifier = modifier
                .size(150.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.Fit
        )
        Text(
            text = titleText,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = subTitleText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogInTitleRow() {
    TitleRow(
        modifier = Modifier,
        titleText = stringResource(id = R.string.LogIn_Title),
        subTitleText = stringResource(id = R.string.LogIn_SubTitle)
    )
}