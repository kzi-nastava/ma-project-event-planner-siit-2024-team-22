package com.example.eventplannerteam22.products.data.model

data class CreateProductDTO(
    val name: String,
    val description: String,
    val price: Double,
    val discount: Double = 0.0,
    val isPrivate: Boolean,
    val userId: Int,
    val productCategoryId: Int
)
