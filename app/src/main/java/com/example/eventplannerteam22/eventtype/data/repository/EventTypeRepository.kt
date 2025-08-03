package com.example.eventplannerteam22.eventtype.data.repository

import com.example.eventplannerteam22.eventtype.domen.EventType
import com.example.eventplannerteam22.eventtype.domen.EventTypeListItem
import com.example.eventplannerteam22.network.ApiResult

interface EventTypeRepository {
    suspend fun getAllEventTypes(): ApiResult<List<EventTypeListItem>>
    suspend fun getEventType(id: Int): ApiResult<EventType>
    suspend fun createEventType(name: String, description: String): ApiResult<Unit>
}