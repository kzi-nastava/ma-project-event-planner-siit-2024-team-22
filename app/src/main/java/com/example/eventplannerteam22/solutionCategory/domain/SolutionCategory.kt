package com.example.eventplannerteam22.solutionCategory.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SolutionCategory(
    val id: Int,
    val name: String,
    val categoryType: String
)