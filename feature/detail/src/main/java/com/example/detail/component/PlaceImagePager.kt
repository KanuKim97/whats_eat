package com.example.detail.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.designsystem.component.EatHorizontalPager
import com.example.designsystem.component.EatImageLoader

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaceImagePager(
    placeImgList: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { placeImgList.size }

    AnimatedVisibility(
        visible = placeImgList.isNotEmpty(),
        enter = fadeIn(),
    ) {
        EatHorizontalPager(
            pagerState = pagerState,
            modifier = modifier
        ) { index ->
            EatImageLoader(
                imageModel = placeImgList[index],
                modifier = modifier.fillMaxSize()
            )
        }
    }
}