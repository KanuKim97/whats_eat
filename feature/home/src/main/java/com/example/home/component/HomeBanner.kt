package com.example.home.component

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.EatShape
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.Typography
import com.example.home.BannerUiState
import com.example.ui.BannerCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeBanner(
    bannerUiState: BannerUiState,
    bannerOnClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    userScrollEnabled: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (bannerUiState) {
            is BannerUiState.IsLoading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    strokeWidth = 3.dp
                )
            }
            is BannerUiState.IsSuccess -> {
                val pagerState = rememberPagerState { bannerUiState.banner?.lastIndex ?: 0 }
                Log.d("로그", "${bannerUiState.banner}")

                HorizontalPager(
                    modifier = modifier.fillMaxSize(),
                    state = pagerState,
                    pageSpacing = 20.dp,
                    userScrollEnabled = userScrollEnabled,
                    pageContent = { index ->
                        BannerCard(
                            banner = bannerUiState.banner?.get(index),
                            bannerOnClick = bannerOnClick
                        )
                    }
                )
            }
            is BannerUiState.IsFailed -> {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .clip(shape = EatShape.large)
                        .background(Gray),
                    contentAlignment = Alignment.Center,
                    content = {
                        Text(
                            text = "로딩에 실패하였습니다.",
                            style = Typography.bodyMedium
                        )
                    }
                )
            }
        }
    }
}