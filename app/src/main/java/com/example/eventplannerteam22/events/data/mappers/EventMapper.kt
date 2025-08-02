package com.example.eventplannerteam22.events.data.mappers

import com.example.eventplannerteam22.events.data.model.EventDTO
import com.example.eventplannerteam22.events.domen.Event
import com.example.eventplannerteam22.events.domen.EventListItem

fun EventDTO.toListItem(): EventListItem {
    return EventListItem(
        this.id,
        this.name,
        this.description,
        this.eventType.name,
        this.location,
        this.user.name + " " + this.user.surname,
//        this.eventDate
    )
}

fun EventDTO.toEvent(): Event {
    return Event(
        this.id,
        this.name,
        this.description,
        this.eventType.name,
        this.maxCapacity,
        this.isPrivate,
        this.location,
//        this.eventDate,
        this.user
    )
}