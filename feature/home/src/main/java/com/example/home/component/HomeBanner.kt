package com.example.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatCircularProgressIndicator
import com.example.designsystem.component.EatHorizontalPager
import com.example.designsystem.theme.EatShape
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.EatTypography
import com.example.home.BannerUiState
import com.example.ui.BannerCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeBanner(
    bannerUiState: BannerUiState,
    bannerOnClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .height(300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (bannerUiState) {
            is BannerUiState.Init -> {  }
            is BannerUiState.IsLoading -> { EatCircularProgressIndicator() }
            is BannerUiState.IsSuccess -> {
                val pagerState = rememberPagerState { bannerUiState.banner?.lastIndex ?: 0 }

                EatHorizontalPager(
                    pagerState = pagerState,
                    modifier = modifier.fillMaxSize()
                ) { index ->
                    BannerCard(
                        banner = bannerUiState.banner?.get(index),
                        bannerOnClick = { bannerOnClick(bannerUiState.banner?.get(index)?.placeID ?: "") }
                    )
                }
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
                            style = EatTypography.bodyMedium
                        )
                    }
                )
            }
        }
    }
}