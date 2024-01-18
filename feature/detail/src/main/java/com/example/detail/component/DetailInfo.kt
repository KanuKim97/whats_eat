package com.example.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Typography
import com.example.detail.DetailUiState

@Composable
fun DetailInfo(
    detailUiState: DetailUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        when (detailUiState) {
            is DetailUiState.IsLoading -> {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                    content = {
                        CircularProgressIndicator(
                            strokeWidth = 3.dp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                )
            }
            is DetailUiState.IsSuccess -> {
                Text(
                    text = detailUiState.info?.name ?: "불러오지 못했습니다.",
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = Typography.headlineMedium
                )
                Text(
                    text = if (detailUiState.info?.opening_hours?.open_now == true) {
                        "영업 중"
                    } else {
                        "영업 종료"
                    },
                    maxLines = 1,
                    style = Typography.labelMedium
                )
            }
            is DetailUiState.IsFailed -> {
                Text(
                    text = "로딩에 실패하였습니다.",
                    style = Typography.labelLarge
                )
            }
        }
    }
}