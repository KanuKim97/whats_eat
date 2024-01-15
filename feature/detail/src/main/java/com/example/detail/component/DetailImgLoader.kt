package com.example.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.theme.EatShape
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.Typography
import com.example.detail.DetailUiState

@Composable
fun DetailImgLoader(
    detailUiState: DetailUiState,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is DetailUiState.IsLoading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primaryContainer,
                strokeWidth = 3.dp
            )
        }
        is DetailUiState.IsSuccess -> {
            EatImageLoader(
                imageModel = detailUiState.info?.photos?.get(0)?.photo_reference ?: "",
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(shape = EatShape.large),
                success = { imgState, _ ->
                    imgState.imageBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Image"
                        )
                    }
                },
                failure = {
                    Box(
                        modifier = modifier
                            .fillMaxSize()
                            .background(color = Gray)
                            .clip(shape = EatShape.large),
                        contentAlignment = Alignment.Center,
                        content = {
                            Text(
                                text = "로딩에 실패하였습니다.",
                                style = Typography.labelLarge
                            )
                        }
                    )
                }
            )
        }
        is DetailUiState.IsFailed -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = Gray)
                    .clip(shape = EatShape.large),
                contentAlignment = Alignment.Center,
                content = {
                    Text(
                        text = "로딩에 실패하였습니다.",
                        style = Typography.labelLarge
                    )
                }
            )
        }
    }

}