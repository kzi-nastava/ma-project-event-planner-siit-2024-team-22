package com.example.eventplannerteam22.favorites.data.api



import com.example.eventplannerteam22.favorites.data.model.FavoriteProductRequestDTO
import com.example.eventplannerteam22.products.domain.Product
import retrofit2.http.*

interface FavoriteProductApi {
    @POST("/products/favorite")
    suspend fun addToFavorites(@Body body: FavoriteProductRequestDTO): Unit

    @GET("/products/favorite/{userId}")
    suspend fun getFavorites(@Path("userId") userId: Int): List<Product>?

    @DELETE("/products/favorite")
    suspend fun removeFromFavorites(
        @Query("userId") userId: Int,
        @Query("productId") productId: Int
    ): Unit
}