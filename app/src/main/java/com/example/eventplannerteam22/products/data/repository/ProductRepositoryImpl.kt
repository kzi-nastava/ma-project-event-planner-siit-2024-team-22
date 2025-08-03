package com.example.eventplannerteam22.products.data.repository

import com.example.eventplannerteam22.products.data.api.ProductApi
import com.example.eventplannerteam22.products.data.model.ProductDTO
import com.example.eventplannerteam22.products.data.toProduct
import com.example.eventplannerteam22.products.data.toProductListItem
import com.example.eventplannerteam22.products.domain.Product
import com.example.eventplannerteam22.products.domain.ProductListItem
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
) : ProductRepository {
    override suspend fun getProducts(limit: Int, offset: Int): List<ProductListItem> {
        return productApi.getProducts(limit, offset).map { it.toProductListItem() }
    }

    override suspend fun getAllProducts(limit: Int, offset: Int): List<Product> {
        return productApi.getProducts(limit, offset).map { it.toProduct() }
    }

    override suspend fun getProduct(id: Int): Product {
        return productApi.getProductById(id).toProduct()
    }

//    override suspend fun addProduct(product: Product) {
//        productApi.addProduct(product)
//    }
}