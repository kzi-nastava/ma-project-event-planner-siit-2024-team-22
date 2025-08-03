package com.example.eventplannerteam22.products.data.api

import com.example.eventplannerteam22.products.data.model.CreateProductDTO
import com.example.eventplannerteam22.products.data.model.ProductDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("/products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<ProductDTO>

    @POST("/products")
    suspend fun createProduct(@Body dto: CreateProductDTO)

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDTO
}