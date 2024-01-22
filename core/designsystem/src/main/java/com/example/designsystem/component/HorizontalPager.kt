package com.example.designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EatHorizontalPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    pageSpacing: Dp,
    userScrollEnabled: Boolean = true,
    pageContent: @Composable (PagerScope.(Int) -> Unit)
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        pageSpacing = pageSpacing,
        userScrollEnabled = userScrollEnabled,
        pageContent = pageContent
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EatHorizontalPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    pageContent: @Composable (PagerScope.(Int) -> Unit)
) {
    EatHorizontalPager(
        pagerState = pagerState,
        modifier = modifier,
        pageSpacing = 20.dp,
        pageContent = pageContent
    )
}