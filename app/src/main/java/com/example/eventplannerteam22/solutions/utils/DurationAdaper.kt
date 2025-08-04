package com.example.eventplannerteam22.solutions.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.Duration

class DurationAdapter {
    @ToJson
    fun toJson(duration: Duration): String {
        return duration.toString()
    }

    @FromJson
    fun fromJson(durationString: String): Duration {
        return Duration.parse(durationString)
    }
}