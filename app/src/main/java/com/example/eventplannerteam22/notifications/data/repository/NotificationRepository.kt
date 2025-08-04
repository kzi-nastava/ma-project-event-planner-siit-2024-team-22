package com.example.eventplannerteam22.notifications.data.repository

import com.example.eventplannerteam22.notifications.data.api.NotificationApi
import com.example.eventplannerteam22.notifications.data.model.NotificationDto
import javax.inject.Inject

interface NotificationRepository {
    suspend fun getNotifications(userId: Int): List<NotificationDto>
}

class NotificationRepositoryImpl @Inject constructor(
    private val api: NotificationApi
) : NotificationRepository {
    override suspend fun getNotifications(userId: Int): List<NotificationDto> {
        return api.getNotifications(userId)
    }
}