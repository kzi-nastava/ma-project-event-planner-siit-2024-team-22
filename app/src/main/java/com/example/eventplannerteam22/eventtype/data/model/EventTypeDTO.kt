package com.example.eventplannerteam22.eventtype.data.model

import com.example.eventplannerteam22.productcategory.data.model.ProductCategoryDTO
import com.example.eventplannerteam22.solutioncategory.data.model.SolutionCategoryDTO
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class EventTypeDTO(
    val id: Int,
    val name: String,
    val description: String,
    val active: Boolean,
    val solutionCategories: List<SolutionCategoryDTO>,
    val productCategories: List<ProductCategoryDTO>
)