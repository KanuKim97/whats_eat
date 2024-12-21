package com.kanukim97.home.component

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
import androidx.compose.ui.unit.dp
import com.kanukim97.designsystem.component.EatCircularProgressIndicator
import com.kanukim97.designsystem.component.EatHorizontalPager
import com.kanukim97.designsystem.theme.EatTypography
import com.kanukim97.home.BannerUiState
import com.kanukim97.ui.BannerCard
import com.kanukim97.home.state.BannerUiState

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
            is BannerUiState.Loading -> { EatCircularProgressIndicator() }
            is BannerUiState.Success -> {
                if (bannerUiState.banner.isNullOrEmpty()) {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "주변에 음식점이 없습니다!",
                            style = EatTypography.bodyMedium
                        )
                    }
                } else {
                    val pagerState = rememberPagerState { bannerUiState.banner.lastIndex }

                    EatHorizontalPager(
                        pagerState = pagerState,
                        modifier = modifier.fillMaxSize()
                    ) { index ->
                        BannerCard(
                            item = bannerUiState.banner[index],
                            itemOnClick = { bannerOnClick(bannerUiState.banner[index].placeId) }
                        )
                    }
                }
            }
            is BannerUiState.Failed -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = {
                        Text(
                            text = "로딩에 실패하였습니다.",
                            style = EatTypography.bodyMedium
                        )
                    }
                )
            }
            is BannerUiState.Empty -> {

            }
        }
    }
}