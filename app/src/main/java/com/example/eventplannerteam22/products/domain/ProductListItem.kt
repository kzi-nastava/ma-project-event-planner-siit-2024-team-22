package com.example.eventplannerteam22.products.domain

import java.math.BigDecimal

data class ProductListItem(
    val id: Int,
    val name: String,
    val price: BigDecimal,
    val discount: BigDecimal,
    val isPrivate: Boolean
)