package com.example.whats_eat.presenter.items.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavBar(
    onHomeBtnClick: () -> Unit,
    onCollectionBtnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentPadding = PaddingValues(10.dp),
        content = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                content = {
                    HomeIcon(onHomeBtnClick)
                    CollectionIcon(onCollectionBtnClick)
                }
            )
        }
    )
}

@Composable
fun HomeIcon(onHomeBtnClick: () -> Unit) {
    IconButton(
        onClick = onHomeBtnClick,
        content = { Icon(imageVector = Icons.Default.Home, contentDescription = "홈") }
    )
}

@Composable
fun CollectionIcon(onCollectionBtnClick: () -> Unit) {
    IconButton(
        onClick = onCollectionBtnClick,
        content = { Icon(imageVector = Icons.Default.Bookmarks, contentDescription = "컬렉션") }
    )
}




@Preview(group = "view")
@Composable
fun PreviewNavBar() {
    BottomNavBar(onHomeBtnClick = {}, onCollectionBtnClick = {})
}

@Preview(group = "item", showBackground = true)
@Composable
fun PreviewNavItem() {
    HomeIcon{}
}

@Preview(group = "item", showBackground = true)
@Composable
fun PreviewNavItem2() {
    CollectionIcon{}
}