package com.example.eventplannerteam22.products

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
    ): List<Product>
    @POST("/products")
    suspend fun addProduct(@Body product: Product)
    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}