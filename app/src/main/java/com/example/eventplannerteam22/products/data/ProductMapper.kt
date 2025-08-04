package com.example.eventplannerteam22.products.data

import com.example.eventplannerteam22.products.data.model.ProductDTO
import com.example.eventplannerteam22.products.domain.Product
import com.example.eventplannerteam22.products.domain.ProductListItem

fun ProductDTO.toProduct(): Product {
    return Product(
        this.id,
        this.name,
        this.description,
        this.price,
        this.discount,
        this.imageSource,
        this.isPrivate,
        this.user,
        this.productCategory
    )
}

fun ProductDTO.toProductListItem(): ProductListItem {
    return ProductListItem(
        this.id,
        this.name,
        this.price,
        this.discount,
        this.isPrivate
    )
}