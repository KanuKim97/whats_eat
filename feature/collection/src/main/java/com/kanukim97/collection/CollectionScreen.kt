package com.kanukim97.collection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanukim97.collection.state.CollectionUiState
import com.kanukim97.designsystem.component.EatCircularProgressIndicator
import com.kanukim97.designsystem.theme.EatTypography
import com.kanukim97.ui.CollectionCard

@Composable
internal fun CollectionRoute(viewModel: CollectionViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CollectionScreen(uiState)
}

@Composable
internal fun CollectionScreen(uiState: CollectionUiState) {
    when (uiState) {
        CollectionUiState.Loading -> {
            EatCircularProgressIndicator()
        }
        CollectionUiState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "저장된 컬렉션이 없습니다.",
                    style = EatTypography.labelLarge
                )
            }
        }
        is CollectionUiState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = uiState.items,
                    key = { item -> item.id }
                ) {
                    CollectionCard(
                        placeName = it.name,
                        placeImgUrl = it.imageUrl
                    )
                }
            }
        }
        is CollectionUiState.Failed -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "데이터를 불러오지 못했습니다.",
                    style = EatTypography.labelLarge
                )
            }
        }
    }
}