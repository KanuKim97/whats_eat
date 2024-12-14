package com.example.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.example.designsystem.theme.EatTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EatCenterAlignedAppBar(
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EatCenterAlignedAppBar(
    navigationIcon: ImageVector,
    navigationIconOnClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    EatCenterAlignedAppBar(
        title = { /* Nothing Here */ },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = navigationIconOnClick,
                content = {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = "Icon Button"
                    )
                }
            )
        },
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EatCenterAlignedAppBar(
    navigationIcon: ImageVector,
    navigationIconOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    EatCenterAlignedAppBar(
        title = { /* Nothing Here */ },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = navigationIconOnClick,
                content = {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = "Icon Button"
                    )
                }
            )
        },
        actions = { /* Nothing Here */ }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EatLargeTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit
) {
    LargeTopAppBar(
        title = title,
        modifier = modifier,
        actions = actions
    )
}

@Composable
fun EatLargeTopAppBar(
    mainTitle: String,
    subTitle: String,
    actionIcon: ImageVector,
    actionIconOnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    EatLargeTopAppBar(
        title = {
            Column {
                Text(
                    text = mainTitle,
                    fontWeight = FontWeight.Bold,
                    style = EatTypography.headlineMedium
                )
                Text(
                    text = subTitle,
                    fontWeight = FontWeight.Medium,
                    style = EatTypography.titleSmall
                )
            }
        },
        modifier = modifier,
        actions = {
            IconButton(
                onClick = actionIconOnClick,
                content = {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = "Icon"
                    )
                }
            )
        }
    )
}