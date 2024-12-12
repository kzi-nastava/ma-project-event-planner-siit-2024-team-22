package com.example.eventplannerteam22.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun TopCarousel(modifier: Modifier = Modifier){
    val carouselItems: List<Pair<String, String>> = listOf(
        "Item 1" to "Description 1",
        "Item 2" to "Description 2",
        "Item 3" to "Description 3",
        "Item 4" to "Description 5",
        "Item 5" to "Description 5"
    )

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        items(carouselItems.size) { index ->
            val item = carouselItems[index] // Access the Pair by index
            CarouselItem(
                modifier = Modifier,
                name = item.first,
                description = item.second
            )
        }
    }
}