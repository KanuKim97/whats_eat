package com.example.collection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.EatCircularProgressIndicator
import com.example.designsystem.component.EatLazyColumn
import com.example.designsystem.theme.Typography
import com.example.ui.CollectionCard

@Composable
internal fun CollectionRoute() {
    val collectionViewModel = hiltViewModel<CollectionViewModel>()
    val deleteContentUiState by collectionViewModel.deleteCollectionState.collectAsStateWithLifecycle()
    val readAllContentUiState by collectionViewModel.readAllCollectionUiState.collectAsStateWithLifecycle()

    CollectionScreen(
        deleteContentUiState = deleteContentUiState,
        readAllContentUiState = readAllContentUiState
    )
}

@Composable
internal fun CollectionScreen(
    deleteContentUiState: DeleteCollectionUiState,
    readAllContentUiState: ReadAllCollectionUiState,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        content = {
            when (readAllContentUiState) {
                is ReadAllCollectionUiState.IsLoading -> { EatCircularProgressIndicator() }
                is ReadAllCollectionUiState.IsSuccess -> {
                    val content = readAllContentUiState.data

                    EatLazyColumn(modifier.fillMaxSize()) {
                        items(items = content) { collection ->
                            CollectionCard(
                                placeName = collection.name,
                                placeImgUrl = collection.imgUrl
                            )
                        }
                    }
                }
                is ReadAllCollectionUiState.IsFailed -> {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                        content = { Text(text = "로딩에 실패하였습니다.", style = Typography.labelLarge) }
                    )
                }
            }
        }
    )
}