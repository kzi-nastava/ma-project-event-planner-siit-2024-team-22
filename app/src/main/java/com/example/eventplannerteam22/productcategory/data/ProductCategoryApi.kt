package com.example.eventplannerteam22.productcategory.data

import com.example.eventplannerteam22.productcategory.domen.ProductCategory

import retrofit2.http.*

interface ProductCategoryApi {
    @GET("/product-categories")
    suspend fun getProductCategories(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<ProductCategory>
    @GET("/product-categories/{id}")
    suspend fun getProductCategoryById(@Path("id") id: Int): ProductCategory
    @POST("/product-categories")
    suspend fun addProductCategory(@Body solution: ProductCategory)
    @PUT("/product-categories")
    suspend fun updateProductCategory(@Body newSolutionCategory: ProductCategory)
    @DELETE("/product-categories")
    suspend fun deleteProductCategory(@Path("id") id: Int)
}