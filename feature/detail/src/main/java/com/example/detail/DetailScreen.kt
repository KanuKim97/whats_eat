package com.example.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.EatTextButton
import com.example.designsystem.theme.Typography
import com.example.detail.component.DetailImgLoader
import com.example.detail.component.DetailInfo
import com.example.detail.component.DetailPlaceView

@Composable
internal fun DetailRoute(addOnCollection: () -> Unit) {
    val detailViewModel = hiltViewModel<DetailViewModel>()
    val detailUiState by detailViewModel.detailUiState.collectAsState()

    DetailScreen(
        detailUiState = detailUiState,
        addOnCollection = addOnCollection
    )
}

@Composable
fun DetailScreen(
    detailUiState: DetailUiState,
    addOnCollection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            DetailImgLoader(detailUiState = detailUiState)
            DetailInfo(detailUiState = detailUiState)
            Spacer(modifier = modifier.size(10.dp))
            DetailPlaceView(detailUiState = detailUiState,)
            EatTextButton(
                onClick = addOnCollection,
                content = { Text(text = "컬렉션에 추가하기", style = Typography.labelLarge) }
            )
        }
    }
}
