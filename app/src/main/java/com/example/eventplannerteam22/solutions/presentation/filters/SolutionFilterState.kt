package com.example.eventplannerteam22.solutions.presentation.filters

import com.example.eventplannerteam22.solutionCategory.domain.SolutionCategory

data class SolutionFilterState(
    val name: String = "",
    val description: String = "",
    val minPrice: String = "",
    val maxPrice: String = "",
    val minDiscount: String = "",
    val maxDiscount: String = "",
    val selectedFilter: String = "",
    val isExpanded: Boolean = false,
    val isCategoryExpanded: Boolean = false,
    val selectedCategory: SolutionCategory? = null,
    val categories: List<SolutionCategory> = emptyList()
)
