package com.example.eventplannerteam22.events.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@JsonClass(generateAdapter = true)
@Parcelize
data class UpdateEventDTO(
    val name: String = "",
    val description: String = "",
    val maxCapacity: Int = 0,
    val location: String = "",
    val eventDate: LocalDate = LocalDate.now()
) : Parcelable
