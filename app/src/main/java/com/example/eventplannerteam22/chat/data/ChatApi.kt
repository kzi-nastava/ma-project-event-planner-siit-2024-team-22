package com.example.eventplannerteam22.chat.data

// ChatApi.kt
import com.example.eventplannerteam22.chat.domen.Conversation
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApi {
    // Add your REST API endpoints here if needed
    // For example:
    @GET("conversations/{userId}")
    suspend fun getConversations(@Path("userId") userId: Int): List<Conversation>

    // Other endpoints...
}