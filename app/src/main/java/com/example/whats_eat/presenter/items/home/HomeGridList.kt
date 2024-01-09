package com.example.whats_eat.presenter.items.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whats_eat.DetailPlaceInfo
import com.example.whats_eat.util.MainGridItems

@Composable
fun HomeGridList(
    gridItems: ArrayList<MainGridItems>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            items(gridItems) { item ->
                HomeGridItem(
                    gridItemTitle = item.name,
                    gridItemImgUri = item.photoRef,
                    gridItemOnClickEvent = {
                        navController.navigateToDetailPage(item.placeID)
                    }
                )
            }
        }
    )
}

private fun NavController.navigateToDetailPage(placeID: String) {
    this.navigate("${DetailPlaceInfo.route}/$placeID")
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeGridList() {
    HomeGridList(gridItems = arrayListOf(), rememberNavController())
}