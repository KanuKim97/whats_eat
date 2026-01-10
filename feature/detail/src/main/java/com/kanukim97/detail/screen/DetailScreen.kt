package com.kanukim97.detail.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.kanukim97.designsystem.component.EatCircularProgressIndicator
import com.kanukim97.designsystem.component.EatImageLoader
import com.kanukim97.designsystem.icons.EatIcons
import com.kanukim97.designsystem.theme.EatShape
import com.kanukim97.designsystem.theme.EatTypography
import com.kanukim97.designsystem.theme.Gray
import com.kanukim97.detail.component.DialIconButton
import com.kanukim97.detail.component.GetDirectionButton
import com.kanukim97.detail.component.OperationalStatusChip
import com.kanukim97.detail.component.RatingAndReviewCount
import com.kanukim97.detail.screen.action.DetailUiAction
import com.kanukim97.detail.screen.state.DetailUiState
import com.kanukim97.ui.cards.ReviewCard
import com.kanukim97.ui.header.ContentHeader

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
        containerColor = MaterialTheme.colorScheme.background
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .matchParentSize(),
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

                            OperationalStatusChip(isOpened = { uiState.info.isOpened })
                        }

                        RatingAndReviewCount(
                            rating = { uiState.info.rating },
                            reviewsCount = { uiState.info.reviewsCount },
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

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
                            GetDirectionButton(
                                onClick = { onAction(DetailUiAction.OnGetDirectionsBtnClick) },
                                modifier = Modifier.weight(1f)
                            )

                            DialIconButton(
                                onClick = { onAction(DetailUiAction.OnDialIconBtnClick) },
                                modifier = Modifier,
                                enabled = uiState.info.phoneNumber.isNotEmpty()
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                        ContentHeader(
                            title = buildAnnotatedString {
                                append("Reviews")
                                append(" ")
                                append("(${uiState.info.reviewsCount})")
                            }.text,
                            action = {
                                Text(
                                    text = "See All",
                                    modifier = Modifier.clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = { onAction(DetailUiAction.OnSeeAllReviewBtnClick) }
                                    ),
                                    style = EatTypography.labelMedium
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        )

                        uiState.info.reviews.forEachIndexed { index, review ->
                            key(index) {
                                ReviewCard(
                                    profileImageUrl = review.userImageUrl,
                                    name = review.authorName,
                                    review = review.text ?: "",
                                    modifier = if (index == uiState.info.reviews.lastIndex) {
                                        Modifier
                                    } else {
                                        Modifier.padding(bottom = 8.dp)
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.size(12.dp))

                        ContentHeader(
                            title = "Location",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
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