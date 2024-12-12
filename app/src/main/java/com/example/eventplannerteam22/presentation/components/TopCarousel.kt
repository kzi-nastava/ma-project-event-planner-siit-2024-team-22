package com.example.eventplannerteam22.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eventplannerteam22.R

@Composable
fun TopCarousel(modifier: Modifier = Modifier) {
    val carouselItems: List<Triple<Int, String, String>> = listOf(
        Triple(R.drawable.sample_image2, "Item 1", "Description 1"),
        Triple(R.drawable.sample_image, "Item 2", "Description 2"),
        Triple(R.drawable.sample_image2, "Item 3", "Description 3"),
        Triple(R.drawable.sample_image, "Item 4", "Description 4"),
        Triple(R.drawable.sample_image2, "Item 5", "Description 5")
    )

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        items(carouselItems.size) { index ->
            val item = carouselItems[index]
            CarouselItem(
                modifier = Modifier,
                imageRes = item.first,
                name = item.second,
                description = item.third
            )
        }
    }
}

@Preview
@Composable
fun PreviewTopCarousel() {
    TopCarousel()
}