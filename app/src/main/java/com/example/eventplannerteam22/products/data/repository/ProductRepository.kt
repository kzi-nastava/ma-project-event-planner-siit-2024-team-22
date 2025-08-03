package com.example.eventplannerteam22.products.data.repository

import com.example.eventplannerteam22.products.data.model.ProductDTO
import com.example.eventplannerteam22.products.domain.Product
import com.example.eventplannerteam22.products.domain.ProductListItem

interface ProductRepository {
    suspend fun getProducts(limit: Int, offset: Int): List<ProductListItem>
    suspend fun getAllProducts(limit: Int, offset: Int): List<Product>
    suspend fun getProduct(id: Int): Product
//    suspend fun createProduct(dto: CreateProductDTO)
}