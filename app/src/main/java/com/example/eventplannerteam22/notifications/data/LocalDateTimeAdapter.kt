package com.example.eventplannerteam22.notifications.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @ToJson
    fun toJson(dateTime: LocalDateTime): String {
        return dateTime.format(formatter)
    }

    @FromJson
    fun fromJson(dateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeString, formatter)
    }
}