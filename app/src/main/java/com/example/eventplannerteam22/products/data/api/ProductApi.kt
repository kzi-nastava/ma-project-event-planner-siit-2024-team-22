package com.example.eventplannerteam22.products.data.api

import com.example.eventplannerteam22.products.data.model.CreateProductDTO
import com.example.eventplannerteam22.products.data.model.ProductDTO
import com.example.eventplannerteam22.products.data.model.UpdateProductDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigDecimal

interface ProductApi {
    @GET("/products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<ProductDTO>

    @POST("/products")
    suspend fun createProduct(@Body dto: CreateProductDTO)

    @POST("products/buy/{eventId}/{productId}")
    suspend fun buyProduct(
        @Path("eventId") eventId: Int,
        @Path("productId") productId: Int
    )

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDTO

    @PUT("/products/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body dto: UpdateProductDTO): ProductDTO

    @GET("/products/search")
    suspend fun searchAndFilterProducts(
        @Query("name") name: String? = null,
        @Query("description") description: String? = null,
        @Query("category") category: String? = null,
        @Query("price") price: BigDecimal? = null,
        @Query("discount") discount: BigDecimal? = null,
        @Query("imageSource") imageSource: String? = null,
        @Query("isPrivate") isPrivate: Boolean? = null
    ): List<ProductDTO>
}