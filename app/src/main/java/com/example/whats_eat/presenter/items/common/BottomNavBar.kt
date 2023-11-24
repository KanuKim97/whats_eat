package com.example.whats_eat.presenter.items.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavBar(modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentPadding = PaddingValues(10.dp),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    HomeIcon()
                    CollectionIcon()
                    ProfileIcon()
                }
            )
        }
    )
}

@Composable
fun HomeIcon() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Image(imageVector = Icons.Default.Home, contentDescription = "")
            Text(text = "홈 화면")
        }
    )
}

@Composable
fun CollectionIcon() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Image(imageVector = Icons.Default.Bookmarks, contentDescription = "")
            Text(text = "컬렉션")
        }
    )
}

@Composable
fun ProfileIcon() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Image(imageVector = Icons.Default.People, contentDescription = "")
            Text(text = "프로필")
        }
    )
}



@Preview(group = "view")
@Composable
fun PreviewNavBar() {
    BottomNavBar()
}

@Preview(group = "item", showBackground = true)
@Composable
fun PreviewNavItem() {
    HomeIcon()
}

@Preview(group = "item", showBackground = true)
@Composable
fun PreviewNavItem2() {
    CollectionIcon()
}

@Preview(group = "item", showBackground = true)
@Composable
fun PreviewNavItem3() {
    ProfileIcon()
}