package com.example.whats_eat.presenter.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whats_eat.presenter.items.home.HomeBannerRow
import com.example.whats_eat.presenter.items.home.HomeGridTitleRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HomeBannerRow(modifier = modifier)
            Card(
                onClick = { /*TODO*/ },
                modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp),
                content = {}
            )
            HomeGridTitleRow(modifier = modifier)
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                content = { /*TODO*/ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    HomePage()
}