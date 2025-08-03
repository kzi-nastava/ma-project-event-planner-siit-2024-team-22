package com.example.eventplannerteam22.solutioncategory.data.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SolutionCategoryDTO(
    val id: Int,
    val name: String
)
