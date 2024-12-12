package com.example.eventplannerteam22.presentation.components


data class CardItem(
    val imageRes: Int, // Ресурс изображения
    val title: String,
    val description: String,
    val type: String // product, service, event
)