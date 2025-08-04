package com.example.eventplannerteam22.eventtype.data.model

import com.example.eventplannerteam22.productcategory.domen.ProductCategory
import com.example.eventplannerteam22.solutionCategory.domain.SolutionCategory
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventTypeDTO(
    val id: Int,
    val name: String,
    val description: String,
    val active: Boolean,
    val solutionCategories: List<SolutionCategory>,
    val productCategories: List<ProductCategory>
)