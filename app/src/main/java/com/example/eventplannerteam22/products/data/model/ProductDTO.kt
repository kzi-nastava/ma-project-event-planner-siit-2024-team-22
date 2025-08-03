package com.example.eventplannerteam22.products.data.model

import java.math.BigDecimal

data class ProductDTO(
    val id: Int,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val discount: BigDecimal? = BigDecimal.ZERO,
    val imageSource: String?,
    val isPrivate: Boolean,
    val isDeleted: Boolean,
    val userId: Int,
    val productCategoryId: Int
)
