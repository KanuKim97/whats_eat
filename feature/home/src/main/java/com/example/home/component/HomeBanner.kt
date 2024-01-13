package com.example.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.home.BannerUiState
import com.example.ui.BannerCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeBanner(
    pagerState: PagerState,
    bannerUiState: BannerUiState,
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
                HorizontalPager(
                    modifier = modifier.fillMaxSize(),
                    state = pagerState,
                    pageSpacing = 20.dp,
                    userScrollEnabled = userScrollEnabled,
                    pageContent = { index ->
                        BannerCard(
                            bannerTitle = bannerUiState.banner?.get(index)?.name.toString(),
                            bannerImgUri = bannerUiState.banner?.get(index)?.photoRef.toString(),
                            bannerOnClick = { /*TODO*/  }
                        )
                    }
                )
            }
            is BannerUiState.IsFailed -> {  }
        }
    }
}