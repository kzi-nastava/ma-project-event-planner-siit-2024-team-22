package com.example.eventplannerteam22.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.eventplannerteam22.R

@Composable
fun CardList(items: List<CardItem>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { item ->
            MyCard(item)
        }
    }
}

val sampleData = listOf(
    CardItem(R.drawable.sample_image, "Card 1", "pisapopa","product"),
    CardItem(R.drawable.sample_image2, "Card 2","popapisa" ,"service"),
    CardItem(R.drawable.sample_image, "Card 3","goida","event")
)
