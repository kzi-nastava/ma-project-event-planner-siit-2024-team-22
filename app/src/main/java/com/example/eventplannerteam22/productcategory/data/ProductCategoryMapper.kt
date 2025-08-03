package com.example.eventplannerteam22.productcategory.data

import com.example.eventplannerteam22.productcategory.data.model.ProductCategoryDTO
import com.example.eventplannerteam22.productcategory.domen.ProductCategory

fun ProductCategoryDTO.toProductCategory(): ProductCategory {
    return ProductCategory(this.id, this.name)
}