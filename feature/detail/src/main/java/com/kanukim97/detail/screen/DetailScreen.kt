package com.kanukim97.detail.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.kanukim97.designsystem.component.EatCircularProgressIndicator
import com.kanukim97.designsystem.component.EatImageLoader
import com.kanukim97.designsystem.theme.EatShape
import com.kanukim97.designsystem.theme.EatTypography
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.kanukim97.designsystem.icons.EatIcons
import com.kanukim97.designsystem.theme.Gray
import com.kanukim97.detail.screen.action.DetailUiAction
import com.kanukim97.detail.screen.state.DetailUiState
import androidx.core.net.toUri

@Composable
internal fun DetailRoute(viewModel: DetailViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.viewModelEvent.collect { event ->
                when (event) {
                    DetailViewModel.Event.NavigateBack -> {

                    }
                    is DetailViewModel.Event.ShowDialog -> {

                    }
                    is DetailViewModel.Event.ShowSnackBar -> {

                    }
                    is DetailViewModel.Event.ShowToast -> {

                    }
                    is DetailViewModel.Event.ShowCallIntent -> {
                        val intent = Intent(Intent.ACTION_DIAL, "tel:${event.phoneNumber}".toUri())
                        context.startActivity(intent)
                    }
                    is DetailViewModel.Event.ShowMapsIntent -> {
                        val intent = Intent(Intent.ACTION_VIEW, "geo:${event.latLng}?q=${event.name}".toUri()).apply {
                            setPackage("com.google.android.apps.maps")
                        }

                        if (intent.resolveActivity(context.packageManager) == null) return@collect

                        context.startActivity(intent)
                    }
                    is DetailViewModel.Event.ShowShareIntent -> {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, event.shareText)
                        }
                        val intentChooser = Intent.createChooser(intent, null)

                        context.startActivity(intentChooser)
                    }
                }
            }
        }
    }

    RestaurantDetailScreen(
        uiState = uiState,
        onAction = viewModel::handleAction
    )
}

@Composable
fun RestaurantDetailScreen(
    uiState: DetailUiState,
    onAction: (DetailUiAction) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            when (uiState) {
                DetailUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        EatCircularProgressIndicator()
                    }
                }
                is DetailUiState.Success -> {
                    val latLng = remember(uiState) {
                        LatLng(uiState.info.latitude, uiState.info.longitude)
                    }
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(latLng, 16f)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(color = Color.DarkGray)
                    ) {
                        Row(
                            modifier = Modifier
                                .height(56.dp)
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                                .fillMaxWidth()
                                .zIndex(1f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { onAction(DetailUiAction.OnBackBtnClick) },
                                modifier = Modifier.size(48.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.White.copy(alpha = 0.2f),
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = "Back",
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                IconButton(
                                    onClick = { onAction(DetailUiAction.OnShareBtnClick) },
                                    modifier = Modifier.size(48.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = Color.White.copy(alpha = 0.2f),
                                        contentColor = Color.White
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Share,
                                        contentDescription = "Back"
                                    )
                                }
                                IconButton(
                                    onClick = { onAction(DetailUiAction.OnAddCollection) },
                                    modifier = Modifier.size(48.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = Color.White.copy(alpha = 0.2f),
                                        contentColor = Color.White
                                    )
                                ) {
                                    Icon(
                                        imageVector = EatIcons.CollectionOutlined,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        }
                        EatImageLoader(
                            imageModel = uiState.info.imageUrl,
                            modifier = Modifier.fillMaxWidth().matchParentSize(),
                            success = { imgState, _ ->
                                imgState.imageBitmap?.let { bitmap ->
                                    Image(
                                        bitmap = bitmap,
                                        modifier = Modifier.matchParentSize(),
                                        contentScale = ContentScale.Crop,
                                        contentDescription = "Image"
                                    )
                                }
                            },
                            failure = {
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(Gray),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        Text(
                                            text = "이미지 불러오기에 실패하였습니다.",
                                            style = EatTypography.labelLarge
                                        )
                                    }
                                )
                            }
                        )
                    }

                    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {

                        Row(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = uiState.info.name,
                                modifier = Modifier.weight(1f),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = EatTypography.headlineLarge
                            )

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xC3C8FCC6)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (uiState.info.isOpened) "Open Now" else "Closed",
                                    modifier = Modifier.padding(8.dp),
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF217523)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.StarRate,
                                contentDescription = "star",
                                tint = Color(0xFFF5C518)
                            )
                            Text(
                                text = uiState.info.rating,
                                style = EatTypography.labelLarge
                            )
                            Text(
                                text = "(69 Reviews)",
                                color = Color.Gray,
                                style = EatTypography.labelMedium.copy(fontWeight = FontWeight.Medium)
                            )
                        }

                        Row(
                            modifier = Modifier.padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = EatIcons.locationOnBordered,
                                contentDescription = "Location",
                                tint = Color.Gray
                            )

                            Text(
                                text = uiState.info.address,
                                style = EatTypography.labelLarge.copy(fontWeight = FontWeight.Medium)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { onAction(DetailUiAction.OnGetDirectionsBtnClick) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.LightGray,
                                    contentColor = Color.DarkGray
                                ),
                                shape = EatShape.large
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Directions,
                                        contentDescription = "Directions",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text("Get Directions")
                                }
                            }

                            IconButton(
                                onClick = { onAction(DetailUiAction.OnCallBtnClick) },
                                modifier = Modifier.border(1.dp, Color.LightGray, EatShape.large),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color(0xFFFF9800)
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = "Call",
                                )
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                        Text(
                            text = "Location",
                            modifier = Modifier.padding(bottom = 12.dp),
                            style = EatTypography.titleMedium
                        )

                        GoogleMap(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(EatShape.large),
                            cameraPositionState = cameraPositionState,
                        ) {
                            Marker(
                                state = MarkerState(position = latLng),
                                title = uiState.info.name
                            )
                        }
                    }
                }
                DetailUiState.Failed -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "로딩에 실패하였습니다.",
                            style = EatTypography.labelLarge
                        )
                    }
                }
            }

        }
    }
}