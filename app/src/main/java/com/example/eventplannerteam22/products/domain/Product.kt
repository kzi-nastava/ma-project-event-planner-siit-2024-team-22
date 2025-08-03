package com.example.eventplannerteam22.products.domain

import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val discount: BigDecimal?,
    val imageSource: String?,
    val isPrivate: Boolean,
    val userId: Int,
    val productCategoryID: Int
)