package com.example.eventplannerteam22.favorites.data.api

import com.example.eventplannerteam22.events.domen.Event
import com.example.eventplannerteam22.favorites.data.model.FavoriteEventRequestDTO
import retrofit2.http.*

interface FavoriteEventApi {
    @POST("/events/favorite")
    suspend fun addToFavorites(@Body body: FavoriteEventRequestDTO): Unit

    @GET("/events/favorite/{userId}")
    suspend fun getFavorites(@Path("userId") userId: Int): List<Event>?

    @DELETE("/events/favorite")
    suspend fun removeFromFavorites(
        @Query("userId") userId: Int,
        @Query("eventId") eventId: Int
    ): Unit
}