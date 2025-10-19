package com.example.eventplannerteam22.products.presentation.filters

import com.example.eventplannerteam22.productcategory.domen.ProductCategory

data class ProductFilterState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val discount: String = "",
    val isPrivate: String = "",
    val selectedFilter: String = "",
    val isExpanded: Boolean = false,
    val isPrivateExpanded: Boolean = false,
    val isCategoryExpanded: Boolean = false,
    val selectedCategory: ProductCategory? = null,
    val categories: List<ProductCategory> = emptyList()
)