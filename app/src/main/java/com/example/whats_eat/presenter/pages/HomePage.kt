package com.example.whats_eat.presenter.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whats_eat.presenter.items.home.HomeBannerPager
import com.example.whats_eat.presenter.items.home.HomeBannerRow
import com.example.whats_eat.presenter.items.home.HomeGridList
import com.example.whats_eat.presenter.items.home.HomeGridTitleRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { 6 })
    
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
            HomeBannerRow()
            HomeBannerPager(pagerState = pagerState)
            HomeGridTitleRow()
            HomeGridList(gridItems = arrayListOf())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    HomePage()
}