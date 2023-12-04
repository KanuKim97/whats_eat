package com.example.whats_eat.presenter.items.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whats_eat.DetailPlaceInfo
import com.example.whats_eat.util.MainBannerItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeBannerPager(
    mainBannerItems: ArrayList<MainBannerItems>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { mainBannerItems.size })

    HorizontalPager(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        state = pagerState,
        pageSpacing = 20.dp,
        userScrollEnabled = true,
        pageContent = { index ->
            HomeBannerItem(
                bannerTitle = mainBannerItems[index].name,
                bannerImageUri = mainBannerItems[index].photoRef,
                bannerOnClickEvent = {
                    navController.navigateToDetailPage(mainBannerItems[index].placeID)
                }
            )
        }
    )
}


private fun NavController.navigateToDetailPage(placeID: String) {
    this.navigate("${DetailPlaceInfo.route}/$placeID")
}

@Preview(showBackground = true)
@Composable
fun PreviewBannerPager() {
    HomeBannerPager(
        navController = rememberNavController(),
        mainBannerItems = arrayListOf()
    )
}