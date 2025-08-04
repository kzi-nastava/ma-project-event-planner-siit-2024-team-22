package com.example.eventplannerteam22.products.domain

import com.example.eventplannerteam22.events.data.model.UserDTO
import com.example.eventplannerteam22.productcategory.domen.ProductCategory
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val discount: BigDecimal,
    val imageSource: String?,
    val isPrivate: Boolean,
    val user: UserDTO,
    val productCategory: ProductCategory
)