package com.example.whats_eat.presenter.items.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeBannerPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        state = pagerState,
        pageSpacing = 20.dp,
        userScrollEnabled = true,
        pageContent = { index ->
            HomeBannerItem(
                bannerTitle = "page $index",
                bannerImageUri = "",
                bannerOnClickEvent = {}
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewBannerPager() {
    HomeBannerPager(pagerState = rememberPagerState( pageCount = { 5 } ))
}