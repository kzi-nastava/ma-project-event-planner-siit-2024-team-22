package com.example.eventplannerteam22.notifications.data.api

import com.example.eventplannerteam22.notifications.data.model.NotificationDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationApi {
    @GET("/notifications/{userId}")
    suspend fun getNotifications(@Path("userId") userId: Int): List<NotificationDto>
}