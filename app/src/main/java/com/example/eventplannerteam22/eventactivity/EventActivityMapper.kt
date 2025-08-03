package com.example.eventplannerteam22.eventactivity

import com.example.eventplannerteam22.eventactivity.data.model.EventActivityDTO
import com.example.eventplannerteam22.eventactivity.domen.EventActivity

fun EventActivityDTO.toEventActivity(): EventActivity {
    return EventActivity(
        this.id,
        this.name
    )
}