package com.kanukim97.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kanukim97.designsystem.component.EatHorizontalPager
import com.kanukim97.designsystem.component.EatImageLoader
import com.kanukim97.ui.preview.ComponentPreview

@Composable
fun EatImageHorizontalPager(
    imageUrlList: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { imageUrlList.size }

    AnimatedVisibility(visible = imageUrlList.isNotEmpty()) {
        EatHorizontalPager(
            pagerState = pagerState,
            modifier = modifier
        ) { index ->
            EatImageLoader(
                imageModel = imageUrlList[index],
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun EatImageHorizontalPagerWithIndicator(
    imageUrlList: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { imageUrlList.size }

    AnimatedVisibility(visible = imageUrlList.isNotEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            EatHorizontalPager(
                pagerState = pagerState,
                modifier = modifier
            ) { index ->
                EatImageLoader(
                    imageModel = imageUrlList[index],
                    modifier = Modifier.fillMaxSize()
                )
            }

            EatPagerIndicators(
                numberOfPages = imageUrlList.size,
                selectedPage = pagerState.currentPage
            )
        }
    }
}


@ComponentPreview
@Composable
fun PreviewPagerWithIndicator() {
    EatImageHorizontalPagerWithIndicator(
        imageUrlList = listOf("", "", "", "", "", ""),
        modifier = Modifier.height(300.dp)
    )
}