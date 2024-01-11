package com.example.whats_eat.presenter.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.model.collection.Collection
import com.example.ui.CollectionItems

@Composable
fun CollectionList(
    listItems: ArrayList<Collection>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = true
    ) {
        items(count = listItems.size) {
            com.example.ui.CollectionItems(
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
    CollectionList(listItems = arrayListOf())
}