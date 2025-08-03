package com.example.eventplannerteam22.eventtype.domen

import com.example.eventplannerteam22.productcategory.domen.ProductCategory
import com.example.eventplannerteam22.solutioncategory.domen.SolutionCategory

data class EventType(
    val id: Int,
    val name: String,
    val description: String,
    val active: Boolean,
    val solutionCategories: List<SolutionCategory>,
    val productCategories: List<ProductCategory>
)