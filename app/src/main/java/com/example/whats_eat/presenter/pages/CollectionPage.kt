package com.example.whats_eat.presenter.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.CollectionItem
import com.example.whats_eat.presenter.items.collection.CollectionItems

@Composable
fun CollectionList(
    modifier: Modifier,
    listState: LazyListState,
    listItems: ArrayList<CollectionItem>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        state = listState,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = true
    ) {
        items(count = listItems.size) {
            CollectionItems(
                modifier = modifier,
                placeName = listItems[it].name.toString(),
                placeAddress = listItems[it].formattedAddress.toString(),
                ratingNumber = listItems[it].rating.toString()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewList() {
    CollectionList(
        modifier = Modifier,
        listState = rememberLazyListState(),
        listItems = arrayListOf()
    )
}