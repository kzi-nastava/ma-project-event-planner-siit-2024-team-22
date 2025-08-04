package com.example.eventplannerteam22.favorites.data.repository

import com.example.eventplannerteam22.events.domen.Event
import com.example.eventplannerteam22.favorites.data.api.*
import com.example.eventplannerteam22.favorites.data.model.*
import com.example.eventplannerteam22.products.domain.Product
import com.example.eventplannerteam22.solutions.domain.Solution
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val eventApi: FavoriteEventApi,
    private val productApi: FavoriteProductApi,
    private val solutionApi: FavoriteSolutionApi
) {
    suspend fun addEventFavorite(dto: FavoriteEventRequestDTO) = eventApi.addToFavorites(dto)

    suspend fun getEventFavorites(userId: Int): List<Event> {
        return try {
            val response = eventApi.getFavorites(userId)
            response ?: emptyList()
        } catch (e: Exception){
            emptyList()
        }
    }

    suspend fun removeEventFavorite(userId: Int, eventId: Int) = eventApi.removeFromFavorites(userId, eventId)

    suspend fun addProductFavorite(dto: FavoriteProductRequestDTO) = productApi.addToFavorites(dto)

    suspend fun getProductFavorites(userId: Int): List<Product> {
        return try {
            val response = productApi.getFavorites(userId)
            response ?: emptyList()
        } catch (e: Exception){
            emptyList()
        }
    }

    suspend fun removeProductFavorite(userId: Int, productId: Int) = productApi.removeFromFavorites(userId, productId)

    suspend fun addSolutionFavorite(dto: FavoriteSolutionRequestDTO) {}

    suspend fun getSolutionFavorites(userId: Int): List<Solution> {
        return try {
            val response = solutionApi.getFavorites(userId)
            response ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun removeSolutionFavorite(userId: Int, solutionId: Int) {}
}
