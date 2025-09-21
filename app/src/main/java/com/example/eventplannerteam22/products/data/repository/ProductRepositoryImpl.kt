package com.example.eventplannerteam22.products.data.repository

import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.safeApiCall
import com.example.eventplannerteam22.products.data.api.ProductApi
import com.example.eventplannerteam22.products.data.model.CreateProductDTO
import com.example.eventplannerteam22.products.data.model.ProductDTO
import com.example.eventplannerteam22.products.data.model.UpdateProductDTO
import com.example.eventplannerteam22.products.data.toProduct
import com.example.eventplannerteam22.products.data.toProductListItem
import com.example.eventplannerteam22.products.domain.Product
import com.example.eventplannerteam22.products.domain.ProductListItem
import okhttp3.OkHttpClient
import java.math.BigDecimal
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val okHttpClient: OkHttpClient
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

    override suspend fun createProduct(dto: CreateProductDTO): ApiResult<Unit> {
        return safeApiCall(okHttpClient) { productApi.createProduct(dto) }
    }

    override suspend fun updateProduct(id: Int, dto: UpdateProductDTO): ApiResult<Unit> {
        return safeApiCall(okHttpClient) { productApi.updateProduct(id, dto) }
    }

    override suspend fun searchAndFilterProducts(
        name: String?,
        description: String?,
        category: String?,
        price: BigDecimal?,
        discount: BigDecimal?,
        imageSource: String?,
        isPrivate: Boolean?
    ): ApiResult<List<ProductDTO>> {
        return safeApiCall(okHttpClient) { 
            productApi.searchAndFilterProducts(
                name = name,
                description = description,
                category = category,
                price = price,
                discount = discount,
                imageSource = imageSource,
                isPrivate = isPrivate
            )
        }
    }
}