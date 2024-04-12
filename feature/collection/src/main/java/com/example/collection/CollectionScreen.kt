package com.example.collection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.designsystem.component.EatCircularProgressIndicator
import com.example.designsystem.component.EatLazyColumn
import com.example.designsystem.theme.EatTypography
import com.example.ui.CollectionCard

@Composable
internal fun CollectionRoute(readAllContentUiState: ReadAllCollectionUiState) {
    CollectionScreen(readAllContentUiState = readAllContentUiState)
}

@Composable
internal fun CollectionScreen(
    readAllContentUiState: ReadAllCollectionUiState,
    modifier: Modifier = Modifier
) {
    when (readAllContentUiState) {
        is ReadAllCollectionUiState.IsLoading -> { EatCircularProgressIndicator() }
        is ReadAllCollectionUiState.IsSuccess -> {
            val content = readAllContentUiState.data
            EatLazyColumn(
                modifier = modifier.fillMaxSize(),
                content = {
                    items(items = content) { collection ->
                        CollectionCard(
                            placeName = collection.name,
                            placeImgUrl = collection.imgUrl
                        )
                    }
                }
            )
        }
        is ReadAllCollectionUiState.IsFailed -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { Text(text = "로딩에 실패하였습니다.", style = EatTypography.labelLarge) }
            )
        }
    }
}