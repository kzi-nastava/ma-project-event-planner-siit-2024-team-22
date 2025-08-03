package com.example.eventplannerteam22.events.data

import com.example.eventplannerteam22.eventactivity.toEventActivity
import com.example.eventplannerteam22.events.data.model.EventDTO
import com.example.eventplannerteam22.events.domen.Event
import com.example.eventplannerteam22.events.domen.EventListItem
import com.example.eventplannerteam22.eventtype.data.toEventType

fun EventDTO.toListItem(): EventListItem {
    return EventListItem(
        this.id,
        this.name,
        this.description,
        this.eventType.name,
        this.location,
        this.user.name + " " + this.user.surname,
        this.eventDate
    )
}

fun EventDTO.toEvent(): Event {
    return Event(
        this.id,
        this.name,
        this.description,
        this.eventType.toEventType(),
        this.maxCapacity,
        this.isPrivate,
        this.location,
        this.eventDate,
        this.user,
        this.eventActivities.map { it.toEventActivity() }
    )
}