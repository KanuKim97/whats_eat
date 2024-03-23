package com.example.collection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.EatCenterAlignedAppBar
import com.example.designsystem.component.EatCircularProgressIndicator
import com.example.designsystem.component.EatLazyColumn
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.EatTypography
import com.example.ui.CollectionCard

@Composable
internal fun CollectionRoute(navigationIconOnClick: () -> Unit) {
    val collectionViewModel = hiltViewModel<CollectionViewModel>()
    val readAllContentUiState by collectionViewModel.readAllCollectionUiState.collectAsStateWithLifecycle()

    CollectionScreen(
        navigationIconOnClick = navigationIconOnClick,
        readAllContentUiState = readAllContentUiState
    )
}

@Composable
internal fun CollectionScreen(
    navigationIconOnClick: () -> Unit,
    readAllContentUiState: ReadAllCollectionUiState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            EatCenterAlignedAppBar(
                navigationIcon = EatIcons.arrowBackOutlined,
                navigationIconOnClick = navigationIconOnClick
            )
        }
    ) { paddingValues ->
        when (readAllContentUiState) {
            is ReadAllCollectionUiState.IsLoading -> { EatCircularProgressIndicator() }
            is ReadAllCollectionUiState.IsSuccess -> {
                val content = readAllContentUiState.data

                EatLazyColumn(
                    modifier = modifier.fillMaxSize(),
                    contentPadding = paddingValues
                ) {
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
                    content = { Text(text = "로딩에 실패하였습니다.", style = EatTypography.labelLarge) }
                )
            }
        }
    }
}