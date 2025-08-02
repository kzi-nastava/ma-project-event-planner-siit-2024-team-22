package com.example.eventplannerteam22.eventType.data

import com.example.eventplannerteam22.eventType.data.model.EventTypeDTO
import com.example.eventplannerteam22.eventType.domen.EventTypeListItem

fun EventTypeDTO.toEventTypeListItem(): EventTypeListItem {
    return EventTypeListItem(
        this.id,
        this.name
    )
}