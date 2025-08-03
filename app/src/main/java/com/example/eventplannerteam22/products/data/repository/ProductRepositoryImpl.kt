package com.example.eventplannerteam22.products.data.repository

import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.safeApiCall
import com.example.eventplannerteam22.products.data.api.ProductApi
import com.example.eventplannerteam22.products.data.model.CreateProductDTO
import com.example.eventplannerteam22.products.data.toProduct
import com.example.eventplannerteam22.products.data.toProductListItem
import com.example.eventplannerteam22.products.domain.Product
import com.example.eventplannerteam22.products.domain.ProductListItem
import okhttp3.OkHttpClient
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val okHttpClient: OkHttpClient
) : ProductRepository {
    override suspend fun getProducts(limit: Int, offset: Int): List<ProductListItem> {
        return productApi.getProducts(limit, offset).map { it.toProductListItem() }
    }

    override suspend fun getProduct(id: Int): Product {
        return productApi.getProductById(id).toProduct()
    }

    override suspend fun createProduct(dto: CreateProductDTO): ApiResult<Unit> {
        return safeApiCall(okHttpClient) { productApi.createProduct(dto) }
    }
}