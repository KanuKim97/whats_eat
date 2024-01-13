package com.example.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.home.ItemGridUiState
import com.example.ui.GridItem

@Composable
fun HomeItemGrid(
    itemGridUiState: ItemGridUiState,
    modifier: Modifier = Modifier,
    gridCells: GridCells = GridCells.Fixed(2)
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (itemGridUiState) {
            is ItemGridUiState.IsLoading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    strokeWidth = 3.dp
                )
            }
            is ItemGridUiState.IsSuccess -> {
                LazyVerticalGrid(
                    columns = gridCells,
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    content = {
                        itemGridUiState.item?.let { itemList ->
                            items(
                                items = itemList,
                                itemContent = { item ->
                                    GridItem(
                                        itemTitle = item.name,
                                        itemImgUri = item.photoRef,
                                        itemOnClick = { /*TODO*/ }
                                    )
                                }
                            )
                        }
                    }
                )
            }
            is ItemGridUiState.IsFailed -> {  }
        }
    }
}