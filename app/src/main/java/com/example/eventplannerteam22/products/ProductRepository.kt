package com.example.eventplannerteam22.products

import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApi: ProductApi
) {
    suspend fun getProducts(limit: Int, offset: Int): List<Product> {
        return productApi.getProducts(limit, offset)
    }
    suspend fun addProduct(product: Product) {
        productApi.addProduct(product)
    }
}