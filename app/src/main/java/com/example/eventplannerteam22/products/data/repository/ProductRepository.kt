package com.example.eventplannerteam22.products.data.repository

import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.products.data.model.CreateProductDTO
import com.example.eventplannerteam22.products.data.model.ProductDTO
import com.example.eventplannerteam22.products.data.model.UpdateProductDTO
import com.example.eventplannerteam22.products.domain.Product
import com.example.eventplannerteam22.products.domain.ProductListItem
import java.math.BigDecimal

interface ProductRepository {
    suspend fun getProducts(limit: Int, offset: Int): List<ProductListItem>
    suspend fun getAllProducts(limit: Int, offset: Int): List<Product>
    suspend fun getProduct(id: Int): Product
    suspend fun createProduct(dto: CreateProductDTO): ApiResult<Unit>
    suspend fun updateProduct(id: Int, dto: UpdateProductDTO): ApiResult<Unit>
    suspend fun searchAndFilterProducts(
        name: String? = null,
        description: String? = null,
        category: String? = null,
        price: BigDecimal? = null,
        discount: BigDecimal? = null,
        imageSource: String? = null,
        isPrivate: Boolean? = null
    ): ApiResult<List<ProductDTO>>
}