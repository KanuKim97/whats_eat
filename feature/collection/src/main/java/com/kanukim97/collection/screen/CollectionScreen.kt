package com.kanukim97.collection.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanukim97.collection.screen.action.CollectionUiAction
import com.kanukim97.collection.screen.state.CollectionUiState
import com.kanukim97.ui.annotation.ComponentPreview

@Composable
internal fun CollectionRoute(viewModel: CollectionViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

//    CollectionScreen(
//        uiState = uiState,
//        onAction = viewModel::handleAction
//    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ComponentPreview
@Composable
internal fun CollectionScreen() {
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { /* Title */ })
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = items.size,
                span = { index ->
                    val span = if (index == 0) maxLineSpan else 1
                    GridItemSpan(span)
                }
            ) { item ->
                Box(modifier = Modifier.background(color = Color.Blue)) {
                    Text(text = items[item])
                }
            }

        }
    }
}