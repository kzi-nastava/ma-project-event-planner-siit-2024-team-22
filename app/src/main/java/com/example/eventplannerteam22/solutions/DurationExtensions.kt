package com.example.eventplannerteam22.solutions

import java.time.Duration

fun Duration.toReadableFormat(): String {
    val hours = this.toHours()
    val minutes = this.minusHours(hours).toMinutes()

    return buildString {
        if (hours > 0) append("${hours} H ")
        if (minutes > 0) append("${minutes} min")
        if (hours == 0L && minutes == 0L) append("0 min")
    }.trim()
}