package com.example.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.designsystem.component.EatCircularProgressIndicator
import com.example.designsystem.component.EatVerticalGrid
import com.example.designsystem.theme.EatTypography
import com.example.home.ItemGridUiState
import com.example.ui.GridItem

@Composable
fun HomeItemGrid(
    itemGridUiState: ItemGridUiState,
    itemOnClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (itemGridUiState) {
            is ItemGridUiState.Init -> {}
            is ItemGridUiState.IsLoading -> { EatCircularProgressIndicator() }
            is ItemGridUiState.IsSuccess -> {
                if (itemGridUiState.item.isNullOrEmpty()) {
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
                    EatVerticalGrid(modifier = modifier.fillMaxSize()) {
                        items(
                            items = itemGridUiState.item,
                            key = { item -> item.placeId }
                        ) { item ->
                            GridItem(item = item, itemOnClick = itemOnClick)
                        }
                    }
                }
            }
            is ItemGridUiState.IsFailed -> {
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
        }
    }
}