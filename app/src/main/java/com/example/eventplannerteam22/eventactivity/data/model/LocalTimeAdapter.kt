package com.example.eventplannerteam22.eventactivity.data.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalTime

class LocalTimeAdapter {
    @FromJson
    fun fromJson(json: String): LocalTime {
        return LocalTime.parse(json) // expects format like "12:00:00"
    }

    @ToJson
    fun toJson(value: LocalTime): String {
        return value.toString() // outputs "12:00:00"
    }
}