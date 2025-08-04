package com.example.eventplannerteam22.chat.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.UUID

class UUIDAdapter {
    @ToJson
    fun toJson(uuid: UUID): String = uuid.toString()

    @FromJson
    fun fromJson(uuidString: String): UUID = UUID.fromString(uuidString)
}