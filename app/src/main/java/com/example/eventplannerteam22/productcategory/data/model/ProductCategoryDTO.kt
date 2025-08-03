package com.example.eventplannerteam22.productcategory.data.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ProductCategoryDTO(
    val id: Int,
    val name: String
)
